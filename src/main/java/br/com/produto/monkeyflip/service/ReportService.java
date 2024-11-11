package br.com.produto.monkeyflip.service;

import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private DataSource dataSource;

    @Value("classpath:/reports/relatorioContasReceber.jrxml")
    private Resource reportContasReceberResource;

    @Value("classpath:/reports/relatorioEstoque.jrxml")
    private Resource reportEstoqueResource;

    // Método para gerar e preencher o relatório de Contas a Receber
    public JasperPrint gerarRelatorioContasReceber(Map<String, Object> parametros) throws JRException, IOException, SQLException {
        return gerarRelatorio(reportContasReceberResource, parametros);
    }

    // Método para gerar e preencher o relatório de Estoque
    public JasperPrint gerarRelatorioEstoque(Map<String, Object> parametros) throws JRException, IOException, SQLException {
        return gerarRelatorio(reportEstoqueResource, parametros);
    }

    // Método genérico para gerar e preencher relatórios
    private JasperPrint gerarRelatorio(Resource reportResource, Map<String, Object> parametros) throws JRException, IOException, SQLException {
        try (InputStream reportStream = reportResource.getInputStream();
             Connection connection = dataSource.getConnection()) {
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            return JasperFillManager.fillReport(jasperReport, parametros, connection);
        }
    }

    // Método para exportar relatório em PDF
    public byte[] exportarRelatorioParaPDF(JasperPrint jasperPrint) throws JRException {
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

}
