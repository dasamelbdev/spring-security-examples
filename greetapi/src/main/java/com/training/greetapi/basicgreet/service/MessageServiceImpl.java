package com.training.greetapi.basicgreet.service;

import com.training.greetapi.basicgreet.dto.MessageRequestDTO;
import com.training.greetapi.basicgreet.dto.MessageResponseDTO;
import com.training.greetapi.basicgreet.entity.UserEntity;
import com.training.greetapi.basicgreet.repository.UserRepository;
import com.training.greetapi.basicgreet.entity.MessageEntity;
import com.training.greetapi.basicgreet.mapper.MessageEntityDTOMapper;
import com.training.greetapi.basicgreet.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageEntityDTOMapper messageEntityDTOMapper;

    /**
     * @param id message id
     * @return {{@link MessageResponseDTO} } found message with given id
     */
    @Override
    public MessageResponseDTO findMessageById(Long id) {
        Optional<MessageEntity> message = messageRepository.findById(id);
        return messageEntityDTOMapper.entityToDTO(message.orElseThrow());
    }

    @Override
    public Page<MessageResponseDTO> findAllMessages(Pageable pageable) {
        Page<MessageEntity> messageEntities = messageRepository.findAll(pageable);
        return messageEntities.map(messageEntityDTOMapper::entityToDTO);

    }

    /**
     * @param requestDto
     * @return
     */
    @Override
    public MessageResponseDTO saveMessage(MessageRequestDTO requestDto) {

        String userName = requestDto.getCreatorName();
        UserEntity user = userRepository.findByUserName(userName).orElseThrow();
        MessageEntity messageEntity = messageEntityDTOMapper.DTOtoEntity(requestDto);
        messageEntity.setUser(user);
        return messageEntityDTOMapper.entityToDTO(messageRepository.save(messageEntity));
    }


    @Override
    public MessageResponseDTO updateMessage(Long id, MessageRequestDTO requestDto) {

        MessageEntity oldMessage = messageRepository.findById(id).orElseThrow();
        oldMessage.setMessage(requestDto.getGreetingMessage());
        oldMessage.setDescription(requestDto.getDescription());
        return messageEntityDTOMapper.entityToDTO(messageRepository.save(oldMessage));
    }


    @Override
    public boolean deleteMessageById(Long id) {
        MessageEntity message = messageRepository.findById(id).orElseThrow();
        messageRepository.delete(message);
        return true;
    }



    @Override
    public Page<UserEntity> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);

    }

}
