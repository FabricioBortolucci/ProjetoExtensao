package br.com.produto.monkeyflip.controller;

import br.com.produto.monkeyflip.service.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller("/relatorio")
@CrossOrigin("*")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/contasReceberRecebidas")
    public String relatorios(Model model) {
        return "relatorios/contasReceberRecebidas";
    }

    @GetMapping("/relatorioEstoque")
    public String relatorioEstoque(Model model) {
        return "relatorios/relatorioEstoque";
    }

    @GetMapping("/gerar-relatorio-contasreceber")
    public void gerarRelatorioContasReceber(@RequestParam(required = false) String dataInicial,
                                            @RequestParam(required = false) String dataFinal,
                                            @RequestParam(required = false) Long vendaId,
                                            HttpServletResponse response) throws Exception {
        Map<String, Object> parametros = new HashMap<>();
        String filtro = "";
        boolean temCondicao = false;

        if (!dataInicial.isEmpty() && !dataFinal.isEmpty()) {
            filtro += " cr.cr_data_vencimento BETWEEN '" + dataInicial + "' AND '" + dataFinal + "'";
            temCondicao = true;
        }

        if (vendaId != null) {
            if (temCondicao) {
                filtro += " AND ";
            }
            filtro += " cr.venda_id = '" + vendaId + "'";
        }

        if (!filtro.isEmpty()) {
            filtro = " WHERE " + filtro;
        }

        parametros.put("filtro", filtro);
        JasperPrint jasperPrint = reportService.gerarRelatorioContasReceber(parametros);
        byte[] pdfBytes = reportService.exportarRelatorioParaPDF(jasperPrint);

        configurarRespostaPDF(response, pdfBytes, "relatorio_contas_receber.pdf");
    }

    @GetMapping("/gerar-relatorio-estoque")
    public void gerarRelatorioEstoque(
            @RequestParam(required = false) Long monkId,
            HttpServletResponse response) throws Exception {
        Map<String, Object> parametros = new HashMap<>();
        String filtro = "";
        boolean temCondicao = false;

        if (monkId != null) {
            filtro += " m.monk_id = '" + monkId + "'";
        }

        if (!filtro.isEmpty()) {
            filtro = " WHERE " + filtro;
        }

        parametros.put("filtro", filtro);

        JasperPrint jasperPrint = reportService.gerarRelatorioEstoque(parametros);
        byte[] pdfBytes = reportService.exportarRelatorioParaPDF(jasperPrint);

        configurarRespostaPDF(response, pdfBytes, "relatorio_estoque.pdf");
    }

    // Método auxiliar para configurar a resposta HTTP para PDF
    private void configurarRespostaPDF(HttpServletResponse response, byte[] pdfBytes, String nomeArquivo) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=" + nomeArquivo);
        response.getOutputStream().write(pdfBytes);
    }
}
