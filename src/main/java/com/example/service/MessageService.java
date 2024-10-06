package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.InvalidMessageBodyException;
import com.example.exception.UserDoesNotExistException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import java.util.Optional;
import java.util.List;

@Service
public class MessageService {
    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message msg) throws InvalidMessageBodyException, UserDoesNotExistException {
        // Validate input (message_text)
        String txt = msg.getMessageText();
        if (txt.length() <= 0 || txt.length() >= 255) {
            throw new InvalidMessageBodyException();
        }

        // Ensure posted_by refers to a real user
        Optional<Account> optionalAccount = accountRepository.findById(msg.getPostedBy());
        if (optionalAccount.isEmpty()) {
            throw new UserDoesNotExistException();
        }

        return messageRepository.save(msg);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer message_id) {
        Optional<Message> optionalMessage = messageRepository.findById(message_id);
        if (optionalMessage.isEmpty()) {
            return null;
        }
        return optionalMessage.get();
    }

    public Integer deleteMessageById(Integer message_id) {
        Optional<Message> optionalMessage = messageRepository.findById(message_id);
        if (optionalMessage.isEmpty()) {
            return null;
        }

        messageRepository.deleteById(message_id);
        return 1;   // This feels hacky. Any better way? (CrudRepository.deleteById is silent upon missing entry)
    }
}
