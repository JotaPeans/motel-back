package com.work.motel.presentation.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mercadopago.resources.point.PointCancelPaymentIntent;
import com.work.motel.application.DTOs.AddReservationToPaymentByIdDTO;
import com.work.motel.application.DTOs.MercadopagoDTO;
import com.work.motel.application.serializers.PixOrder;
import com.work.motel.application.serializers.PointOrder;
import com.work.motel.application.service.PagamentoService;
import com.work.motel.domain.entities.Pagamento;
import com.work.motel.infrastructure.integrations.MercadopagoIntegration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RequestMapping("/payment")
@RestController
public class pagamentoController extends PrivateController {

    @Autowired
    private PagamentoService paymentService; // Injeção de dependência diretamente no campo

    @Autowired
    private MercadopagoIntegration mercadopagoIntegration;

    @GetMapping
    public ResponseEntity<List<Pagamento>> getPagamentos(
            HttpServletRequest request,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Pagamento> response = paymentService.getAll(page, size);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/provider/pix")
    public ResponseEntity<?> CreatePixPayment(@RequestBody @Valid MercadopagoDTO data) {
        mercadopagoIntegration.init();

        PixOrder order = mercadopagoIntegration.createPixOrder(data);

        if (order == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao solicitar pagamento por pix");
        }

        return ResponseEntity.ok(order);
    }

    @PostMapping("/provider/credit")
    public ResponseEntity<?> CreateCreditPayment(@RequestBody @Valid MercadopagoDTO data) {
        mercadopagoIntegration.init();

        PointOrder order = mercadopagoIntegration.createCreditOrder(data);

        if (order == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao solicitar pagamento por credito");
        }

        return ResponseEntity.ok(order);
    }

    @PostMapping("/provider/debit")
    public ResponseEntity<?> CreateDebitPayment(@RequestBody @Valid MercadopagoDTO data) {
        mercadopagoIntegration.init();

        PointOrder order = mercadopagoIntegration.createDebitOrder(data);

        if (order == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao solicitar pagamento por debito");
        }

        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/provider/pdv/{id}")
    public ResponseEntity<?> CancelPdvPayment(@PathVariable String id) {
        mercadopagoIntegration.init();

        PointCancelPaymentIntent data = mercadopagoIntegration.cancelPdvPayment(id);

        if (data == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao solicitar pagamento por debito");
        }

        return ResponseEntity.ok(data);
    }

    @GetMapping("/provider/{id}")
    public ResponseEntity<Optional<Pagamento>> getPagamentoByProviderId(@PathVariable String id) {
        Optional<Pagamento> response = paymentService.getByPaymentProviderId(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Optional<Pagamento>> createPagamento(@RequestBody Pagamento data) {
        Optional<Pagamento> response = paymentService.create(Optional.of(data));
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> addReservationToPaymentById(@PathVariable Integer id, @RequestBody AddReservationToPaymentByIdDTO data) {
        paymentService.addReservation(id, data.getReservaId());
        return ResponseEntity.ok("Reserva associada com sucesso!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Pagamento>> getPagamentoById(@PathVariable Integer id) {
        Optional<Pagamento> response = paymentService.getById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<Pagamento>> deletePagamento(@PathVariable Integer id) {
        paymentService.delete(id);
        return ResponseEntity.ok(null);
    }

}
