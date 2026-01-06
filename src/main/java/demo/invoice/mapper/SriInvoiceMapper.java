package demo.invoice.mapper;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import demo.invoice.context.InvoiceContext;
import demo.invoice.dto.request.InvoiceRequest;
import demo.invoice.dto.request.InvoiceRequest.InvoiceDetailRequest;
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
import demo.invoice.util.ClaveAccesoUtil;
import demo.invoice.util.NumericCodeGeneratorUtil;

@Component
public class SriInvoiceMapper {

    public SriInvoiceXml mapToSriInvoiceMapper(InvoiceRequest request, InvoiceContext context){
        SriInvoiceXml sriInvoiceXml = new SriInvoiceXml();
        sriInvoiceXml.setInfoTributaria(mapTributaryInfoXml(context.getIssuer(), context.getIssuerConfig(), context.getNextInvoiceSequential()));
        sriInvoiceXml.setDetalles(mapToDetailInvoiceXml(request.getDetails()));
        sriInvoiceXml.setInfoFactura(mapToInvoiceInfoXml(request, sriInvoiceXml.getDetalles()));
        return sriInvoiceXml;
    }
    
    private SriTributaryInfoXml mapTributaryInfoXml(Issuer issuer, IssuerConfig issuerConfig, String nextInvoiceSequential){
        
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
        infoTributaria.setClaveAcceso(ClaveAccesoUtil.generarClaveAcceso(    
            new Date(),                             // Fecha de emisión
            "01",                   // Tipo comprobante (factura)
            issuer.getRuc(),                        // RUC del emisor
            issuerConfig.getEnvironment(),                   // Ambiente: 1=pruebas, 2=producción
            issuerConfig.getEstablishmentCode() + issuerConfig.getEmissionPointCode(),              // Serie: establecimiento+puntoEmision
            nextInvoiceSequential,           // Secuencial
            NumericCodeGeneratorUtil.generate(),            // Código numérico (aleatorio o incremental)
            issuerConfig.getEmissionType()                    // Tipo de emisión
        )
       );
       return infoTributaria;
    }

    private List<SriDetailInvoiceXml> mapToDetailInvoiceXml(List<InvoiceDetailRequest> details){
        List<SriDetailInvoiceXml> detalleFacturas = new ArrayList<>();

        for (InvoiceDetailRequest detail : details) {
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

    private SriInvoiceInfoXml mapToInvoiceInfoXml(InvoiceRequest request, List<SriDetailInvoiceXml> details){
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        SriInvoiceInfoXml infoFactura = new SriInvoiceInfoXml();
        infoFactura.setFechaEmision(sdf.format(new Date()));
        infoFactura.setDirEstablecimiento(request.getBuyerAddress());
        infoFactura.setObligadoContabilidad("SI");
        infoFactura.setTipoIdentificacionComprador(request.getBuyerIdentificationType());
        infoFactura.setRazonSocialComprador(request.getBuyerName());
        infoFactura.setIdentificacionComprador(request.getBuyerIdentification());
        infoFactura.setTotalSinImpuestos(
            details.stream()
                .map(SriDetailInvoiceXml :: getPrecioTotalSinImpuesto)
                .reduce(BigDecimal.ZERO, BigDecimal :: add)
        );
        infoFactura.setTotalDescuento(
            details.stream()
                .map(SriDetailInvoiceXml :: getDescuento)
                .reduce(BigDecimal.ZERO, BigDecimal :: add)
        );

        Map<String, SriTotalTaxXml> taxMap = new HashMap<>();

        for (SriDetailInvoiceXml detail : details) {
            for (SriTaxXml tax : detail.getImpuestos().getImpuesto()) {

                String key = tax.getCodigo() + "-" + tax.getCodigoPorcentaje();

                SriTotalTaxXml totalTax = taxMap.computeIfAbsent(key, k -> {
                    SriTotalTaxXml t = new SriTotalTaxXml();
                    t.setCodigo(tax.getCodigo());
                    t.setCodigoPorcentaje(tax.getCodigoPorcentaje());
                    t.setBaseImponible(BigDecimal.ZERO);
                    t.setValor(BigDecimal.ZERO);
                    return t;
                });

                totalTax.setBaseImponible(
                    totalTax.getBaseImponible().add(tax.getBaseImponible())
                );
                totalTax.setValor(
                    totalTax.getValor().add(tax.getValor())
                );
            }
        }

        SriTotalWithTaxesXml sriTotalWithTaxesXm = new SriTotalWithTaxesXml();
        sriTotalWithTaxesXm.getTotalImpuesto().addAll(taxMap.values());

        infoFactura.setTotalConImpuestos(sriTotalWithTaxesXm.getTotalImpuesto());

        infoFactura.setPropina(BigDecimal.ZERO);
        infoFactura.setImporteTotal(
            infoFactura.getTotalSinImpuestos().add(
                taxMap.values().stream()
                    .map(SriTotalTaxXml :: getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal :: add)
            )
        );
        infoFactura.setMoneda("DOLAR");

        return infoFactura;
    }
}
