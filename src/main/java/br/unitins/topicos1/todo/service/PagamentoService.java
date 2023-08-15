//package br.unitins.topicos1.todo.service;
//
//import br.unitins.topicos1.domain.exception.PagamentoNaoEncontradoException;
//import br.unitins.topicos1.domain.model.Pagamento;
//import br.unitins.topicos1.todo.domain.repository.PagamentoRepository;
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.inject.Inject;
//import jakarta.ws.rs.NotFoundException;
//
//@ApplicationScoped
//public class PagamentoService {
//    @Inject
//    PagamentoRepository pagamentoRepository;
//
//    public Pagamento buscarPorId(Long id) {
//        try {
//            return pagamentoRepository.buscarPagamentoPorId(id);
//        } catch (NotFoundException e) {
//            throw new PagamentoNaoEncontradoException("Pagamento não encontrado.");
//        }
//    }

//    public boolean pagamentoAtrasadoExiste(Perfil perfil) {
//        return pagamentoRepository.pagamentoEmAtraso(perfil);
//    }

//    public Pagamento salvar(FormaPagamento formaPagamento, PreCompra preCompra) {
//        Pagamento pagamento = new Pagamento();
//        pagamento.dataEmissao = now();
//        pagamentoRepository.persist(pagamento);
//
//        definirStatusPagamento(pagamento);
//
//        preCompra.pagamento = pagamento;
//        return pagamento;
//    }

//    private void definirStatusPagamento(Pagamento pagamento) {
//        switch (pagamento.getFormaPagamento()) {
//            case BOLETO, TRANSFERENCIA, PIX -> pagamento.setStatusPagamento(StatusPagamento.PENDENTE);
//            case CARTAO_CREDITO, CARTAO_DEBITO -> pagamento.setStatusPagamento(StatusPagamento.EM_ANALISE);
//            default ->
//                    throw new PagamentoConflitoException("Forma de pagamento inválida: " + pagamento.getFormaPagamento());
//        }
//    }

//    public Pagamento validarPagamentoParcelado(PreCompra preCompra) {
//        Pagamento pagamento = buscarPorId(preCompra.pagamento.getId());
//
//        if (pagamento.getFormaPagamento() != FormaPagamento.CARTAO_CREDITO) {
//            throw new PagamentoConflitoException("Forma de pagamento inválida:");
//        }
//        return pagamento;
//    }
//}
