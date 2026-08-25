package com.cefet.bakefy.service;

import java.util.List;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.cefet.bakefy.dto.NotificacaoDTO;
import com.cefet.bakefy.entity.Cliente;
import com.cefet.bakefy.entity.DispositivoCliente;
import com.cefet.bakefy.entity.Produto;
import com.cefet.bakefy.repository.ClienteRepository;
import com.cefet.bakefy.repository.DispositivoClienteRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

@Service
public class NotificacaoService {

    private final SimpMessagingTemplate messagingTemplate;
    private final ClienteRepository clienteRepository;
    private final DispositivoClienteRepository dispositivoClienteRepository;

    public NotificacaoService(
            SimpMessagingTemplate messagingTemplate,
            ClienteRepository clienteRepository,
            DispositivoClienteRepository dispositivoClienteRepository) {
        this.messagingTemplate = messagingTemplate;
        this.clienteRepository = clienteRepository;
        this.dispositivoClienteRepository = dispositivoClienteRepository;
    }

    public void notificarMudancaDeStatus(Produto produto) {

        List<Cliente> clientesQueFavoritaram =
                clienteRepository.findByProdutos_IdProduto(produto.getIdProduto());

        if (clientesQueFavoritaram.isEmpty()) {
            return;
        }

        boolean disponivel = "true".equals(produto.getStatus());

        String mensagem = disponivel
                ? produto.getNmProduto() + " está disponível novamente!"
                : produto.getNmProduto() + " ficou indisponível.";

        NotificacaoDTO notificacao = new NotificacaoDTO(
                produto.getIdProduto(),
                produto.getNmProduto(),
                disponivel,
                mensagem
        );

        for (Cliente cliente : clientesQueFavoritaram) {
            messagingTemplate.convertAndSend(
                    "/topico/favoritos/" + cliente.getIdUsuario(),
                    notificacao
            );

            enviarPushParaCliente(cliente.getIdUsuario(), "Bakefy", mensagem);
        }
    }

    private void enviarPushParaCliente(Integer idCliente, String titulo, String corpo) {

        List<DispositivoCliente> dispositivos =
                dispositivoClienteRepository.findByCliente_IdUsuario(idCliente);

        for (DispositivoCliente dispositivo : dispositivos) {

            Message mensagemPush = Message.builder()
                    .setToken(dispositivo.getTokenFcm())
                    .setNotification(
                            Notification.builder()
                                    .setTitle(titulo)
                                    .setBody(corpo)
                                    .build()
                    )
                    .build();

            try {
                FirebaseMessaging.getInstance().send(mensagemPush);
            } catch (FirebaseMessagingException e) {
                dispositivoClienteRepository.delete(dispositivo);
            }
        }
    }
}

