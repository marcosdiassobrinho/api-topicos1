package br.unitins.topicos1.service;

import br.unitins.topicos1.domain.model.Pagamento;
import br.unitins.topicos1.domain.model.Parcela;
import br.unitins.topicos1.todo.domain.repository.ParcelaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@ApplicationScoped
public class ParcelaService {
    @Inject
    ParcelaRepository parcelaRepository;
    @Inject
    PagamentoService pagamentoService;

    public Parcela buscarPorId(Long id) {
        return parcelaRepository.buscarParcelaPorId(id)
                .orElseThrow();
    }

    @Transactional
    public double criarParcelas(int parcelasQuant, Pagamento pagamento, double valorTotal) {
        final double juros = 1.02;
        double valorTotalComJuros;

        if (parcelasQuant > 3) {
            valorTotalComJuros = valorTotal * Math.pow(juros, parcelasQuant - 2);
        } else {
            valorTotalComJuros = valorTotal;
        }

        double parcelaValor = valorTotalComJuros / parcelasQuant;

        List<Parcela> parcelas = IntStream.range(0, parcelasQuant)
                .mapToObj(i -> {
                    Parcela parcela = new Parcela();
                    parcela.setNumero(i + 1);
                    double valorParcela = Math.round(parcelaValor * 100.0) / 100.0;
                    parcela.setValor(valorParcela);
                    parcela.setPagamento(pagamento);
                    parcela.persistAndFlush();
                    return parcela;
                })
                .toList();

        pagamento.parcelas = parcelas;
        return parcelas.stream()
                .mapToDouble(Parcela::getValor)
                .sum();
    }


    public void salvar(Parcela parcela) {
        parcelaRepository.persist(parcela);
    }


}
