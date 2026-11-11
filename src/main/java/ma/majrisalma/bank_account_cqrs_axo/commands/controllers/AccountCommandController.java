package ma.majrisalma.bank_account_cqrs_axo.commands.controllers;

import ma.majrisalma.bank_account_cqrs_axo.commands.commands.AddAccountCommand;
import ma.majrisalma.bank_account_cqrs_axo.commands.commands.CreditAccountCommand;
import ma.majrisalma.bank_account_cqrs_axo.dtos.AddNewAccountRequestDTO;
import ma.majrisalma.bank_account_cqrs_axo.dtos.CreditAccountRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.FilteringEventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/commands/accounts")
public class AccountCommandController {
    private CommandGateway commandGateway;
    private EventStore eventStore;
    public AccountCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/add")
    public CompletableFuture<String> addNewAccount(@RequestBody AddNewAccountRequestDTO request) {
        CompletableFuture<String> response = commandGateway.send(new AddAccountCommand(
                UUID.randomUUID().toString(),
                request.initialBalance(),
                request.currency()
        ));
        return response;
    }


    @PostMapping("/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDTO request){
        CompletableFuture<String> response = commandGateway.send(new CreditAccountCommand(
                request.accountId(),
                request.amount(),
                request.currency()
        ));
        return response;
    }

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception exception){
        return exception.getMessage();
    }


    @GetMapping("/events/{accountId}")
    public Stream eventStore(@PathVariable String accountId){

        return eventStore.readEvents(accountId).asStream();
    }
}