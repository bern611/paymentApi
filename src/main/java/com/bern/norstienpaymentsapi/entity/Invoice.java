/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bern.norstienpaymentsapi.entity;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author unknown
 */
@Entity
public class Invoice extends IdentifiableByUUID {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String invoiceNumber;

    private LocalDate invoiceDate, paymentDueDate, paidDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lease_id", referencedColumnName = "id")
    private Lease lease;

    @OneToMany(/*cascade = {CascadeType.REMOVE},*/ mappedBy = "invoice", orphanRemoval=true)
    private List<Payment> payments;
    
    @OneToMany(/*cascade = {CascadeType.REMOVE},*/ mappedBy = "invoice", orphanRemoval=true)
    private List<LineItem> lineItems;

    public Invoice() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public LocalDate getPaymentDueDate() {
        return paymentDueDate;
    }

    public void setPaymentDueDate(LocalDate paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }

    public LocalDate getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(LocalDate paidDate) {
        this.paidDate = paidDate;
    }

    public Lease getLease() {
        return lease;
    }

    public void setLease(Lease lease) {
        this.lease = lease;
    }

    /* public List<Payment> getPayments() {
    return payments;
    }
    
    public void setPayments(List<Payment> payments) {
    this.payments = payments;
    }
    
    public List<LineItem> getLineItems() {
    return lineItems;
    }
    
    public void setLineItems(List<LineItem> lineItems) {
    this.lineItems = lineItems;
    }*/

    
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Invoice)) {
            return false;
        }

        Invoice other = (Invoice) o;

        return (id != null && id.equals(other.getId()));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
