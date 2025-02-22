package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.*;
import com.example.exception.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.ArrayList;

import javax.websocket.server.PathParam;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    // NOTE:    Methods below throw custom exceptions on invalid inputs. Custom exceptions
    //          have been annotated w/ @ResponseStatus to set the status code accordingly

    @PostMapping(value = "/register")
    public Account registerUser(@RequestBody Account acc) {
        return accountService.registerAccount(acc);
    }

    @PostMapping(value = "/login")
    public Account loginUser(@RequestBody Account acc) {
        return accountService.accountExists(acc);
    }

    @PostMapping(value = "/messages")
    public Message createMessage(@RequestBody Message msg) {
        return messageService.createMessage(msg);
    }

    @GetMapping(value = "/messages")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping(value = "/messages/{message_id}")
    public Message getMessageById(@PathVariable Integer message_id) {
        return messageService.getMessageById(message_id);
    }

    @DeleteMapping(value = "/messages/{message_id}")
    public Integer deleteMessageById(@PathVariable Integer message_id) {
        return messageService.deleteMessageById(message_id);
    }

    @PatchMapping(value = "/messages/{message_id}")
    public Integer updateMessageById(@PathVariable Integer message_id, @RequestBody Message msg) {    
        // NOTE: @Param msg not guaranteed to have any value other than message_text 
        return messageService.updateMessageById(message_id, msg.getMessageText());
    }

    @GetMapping(value = "/accounts/{account_id}/messages")
    public List<Message> getAllMessagesFromAccountById(@PathVariable Integer account_id) {
        return messageService.getAllMessagesFromAccountById(account_id);
    }
}
