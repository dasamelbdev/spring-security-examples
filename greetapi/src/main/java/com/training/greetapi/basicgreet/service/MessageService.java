package com.training.greetapi.basicgreet.service;

import com.training.greetapi.basicgreet.dto.MessageRequestDTO;
import com.training.greetapi.basicgreet.dto.MessageResponseDTO;
import com.training.greetapi.basicgreet.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {

    MessageResponseDTO findMessageById(Long id);

    MessageResponseDTO saveMessage(MessageRequestDTO RequestDto);

    Page<MessageResponseDTO> findAllMessages(Pageable pageable);

    MessageResponseDTO updateMessage(Long id, MessageRequestDTO requestDto);

    boolean deleteMessageById(Long id);

    Page<UserEntity> findAllUsers(Pageable pageable);
}
