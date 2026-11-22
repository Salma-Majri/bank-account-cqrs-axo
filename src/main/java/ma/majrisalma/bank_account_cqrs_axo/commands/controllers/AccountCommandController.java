package ma.majrisalma.bank_account_cqrs_axo.commands.controllers;

import ma.majrisalma.bank_account_cqrs_axo.commands.commands.AddAccountCommand;
import ma.majrisalma.bank_account_cqrs_axo.commands.commands.CreditAccountCommand;
import ma.majrisalma.bank_account_cqrs_axo.commands.commands.DebitAccountCommand;
import ma.majrisalma.bank_account_cqrs_axo.commands.commands.UpdateAccountStatusCommand;
import ma.majrisalma.bank_account_cqrs_axo.dtos.AddNewAccountRequestDTO;
import ma.majrisalma.bank_account_cqrs_axo.dtos.CreditAccountRequestDTO;
import ma.majrisalma.bank_account_cqrs_axo.dtos.DebitAccountRequestDTO;
import ma.majrisalma.bank_account_cqrs_axo.dtos.UpdateAccountStatusRequestDTO;
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

    public AccountCommandController(CommandGateway commandGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
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

    @PostMapping("/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO request){
        CompletableFuture<String> response = commandGateway.send(new DebitAccountCommand(
                request.accountId(),
                request.amount(),
                request.currency()
        ));
        return response;
    }


    @PutMapping("/updateStatus")
    public CompletableFuture<String> updateStatus(@RequestBody UpdateAccountStatusRequestDTO request) {
        CompletableFuture<String> response = commandGateway.send(new UpdateAccountStatusCommand(
                request.accountId(),
                request.status()
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