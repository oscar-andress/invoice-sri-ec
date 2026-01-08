package demo.invoice.mapper;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import demo.invoice.domain.calculator.InvoiceTaxTotal;
import demo.invoice.domain.calculator.InvoiceTotals;
import demo.invoice.domain.context.InvoiceContext;
import demo.invoice.dto.request.IssueInvoiceRequest;
import demo.invoice.dto.request.IssueInvoiceRequest.IssueInvoiceDetailRequest;
import demo.invoice.entity.Issuer;
import demo.invoice.entity.IssuerConfig;
import demo.invoice.sri.xml.SriDetailInvoiceXml;
import demo.invoice.sri.xml.SriInvoiceInfoXml;
import demo.invoice.sri.xml.SriInvoiceXml;
import demo.invoice.sri.xml.SriTaxXml;
import demo.invoice.sri.xml.SriTaxesXml;
import demo.invoice.sri.xml.SriTotalTaxXml;
import demo.invoice.sri.xml.SriTotalWithTaxesXml;
import demo.invoice.sri.xml.SriTributaryInfoXml;

@Component
public class SriInvoiceMapper {

    public SriInvoiceXml mapToSriInvoiceMapper(IssueInvoiceRequest request, InvoiceContext context, InvoiceTotals invoiceTotals){
        SriInvoiceXml sriInvoiceXml = new SriInvoiceXml();
        sriInvoiceXml.setInfoTributaria(mapTributaryInfoXml(context.getIssuer(), context.getIssuerConfig(), context.getNextInvoiceSequential(), context.getAccessKey()));
        sriInvoiceXml.setDetalles(mapToDetailInvoiceXml(request.getDetails()));
        sriInvoiceXml.setInfoFactura(mapToInvoiceInfoXml(request, invoiceTotals));
        return sriInvoiceXml;
    }
    
    private SriTributaryInfoXml mapTributaryInfoXml(Issuer issuer, IssuerConfig issuerConfig, String nextInvoiceSequential, String accessKey){
        
        SriTributaryInfoXml infoTributaria = new SriTributaryInfoXml();
        infoTributaria.setAmbiente(issuerConfig.getEnvironment());
        infoTributaria.setRuc(issuer.getRuc());
        infoTributaria.setRazonSocial(issuer.getLegalName());
        infoTributaria.setCodDoc("01");
        infoTributaria.setTipoEmision(issuerConfig.getEmissionType());
        infoTributaria.setEstab(issuerConfig.getEstablishmentCode());
        infoTributaria.setPtoEmi(issuerConfig.getEmissionPointCode());
        infoTributaria.setSecuencial(nextInvoiceSequential);
        infoTributaria.setDirMatriz(issuer.getHeadOfficeAddress());
        infoTributaria.setClaveAcceso(accessKey);
       return infoTributaria;
    }

    private List<SriDetailInvoiceXml> mapToDetailInvoiceXml(List<IssueInvoiceDetailRequest> details){
        List<SriDetailInvoiceXml> detalleFacturas = new ArrayList<>();

        for (IssueInvoiceDetailRequest detail : details) {
            SriDetailInvoiceXml detalleFactura = new SriDetailInvoiceXml();
            detalleFactura.setCantidad(detail.getQuantity());
            detalleFactura.setPrecioUnitario(detail.getUnitPrice());
            detalleFactura.setDescripcion(detail.getDescription());
            detalleFactura.setPrecioSinSubsidio(BigDecimal.ZERO);
            detalleFactura.setDescuento(detail.getDiscount());
            detalleFactura.setPrecioTotalSinImpuesto(detalleFactura.getCantidad().multiply(detalleFactura.getPrecioUnitario()).subtract(detalleFactura.getDescuento()));
            detalleFacturas.add(detalleFactura);


            SriTaxXml imp = new SriTaxXml();
            imp.setCodigo(detail.getTaxCode()); // IVA
            imp.setCodigoPorcentaje(detail.getTaxPercentageCode()); // IVA 12%
            imp.setTarifa(detail.getTaxPercentage());
            imp.setBaseImponible(detalleFactura.getPrecioTotalSinImpuesto());
            imp.setValor(imp.getBaseImponible().multiply(imp.getTarifa()).divide(new BigDecimal("100")));

            SriTaxesXml impuestos = new SriTaxesXml();
            impuestos.getImpuesto().add(imp);

            detalleFactura.setImpuestos(impuestos);
        }

        return detalleFacturas;
    }

    private SriInvoiceInfoXml mapToInvoiceInfoXml(IssueInvoiceRequest request, InvoiceTotals invoiceTotals){
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        SriInvoiceInfoXml infoFactura = new SriInvoiceInfoXml();
        infoFactura.setFechaEmision(sdf.format(new Date()));
        infoFactura.setDirEstablecimiento(request.getBuyerAddress());
        infoFactura.setObligadoContabilidad("SI");
        infoFactura.setTipoIdentificacionComprador(request.getBuyerIdentificationType());
        infoFactura.setRazonSocialComprador(request.getBuyerName());
        infoFactura.setIdentificacionComprador(request.getBuyerIdentification());
        infoFactura.setTotalSinImpuestos(invoiceTotals.getTotalWithoutTaxes());
        infoFactura.setTotalDescuento(invoiceTotals.getTotalDiscount());

        SriTotalWithTaxesXml sriTotalWithTaxesXml = mapToTotalWithTaxesXml(invoiceTotals.getTaxTotals().values());

        infoFactura.setTotalConImpuestos(sriTotalWithTaxesXml.getTotalImpuesto());

        infoFactura.setPropina(BigDecimal.ZERO);
        infoFactura.setImporteTotal(invoiceTotals.getGrandTotal());
        infoFactura.setMoneda("DOLAR");

        return infoFactura;
    }

    private SriTotalWithTaxesXml mapToTotalWithTaxesXml(Collection<InvoiceTaxTotal> taxTotals) {

        SriTotalWithTaxesXml sriTaxes = new SriTotalWithTaxesXml();

        for (InvoiceTaxTotal tax : taxTotals) {
            SriTotalTaxXml sriTax = new SriTotalTaxXml();
            sriTax.setCodigo(tax.getTaxCode());
            sriTax.setCodigoPorcentaje(tax.getTaxPercentageCode());
            sriTax.setBaseImponible(tax.getTaxableBase());
            sriTax.setValor(tax.getTaxValue());

            sriTaxes.getTotalImpuesto().add(sriTax);
        }

        return sriTaxes;
    }
}
