package fi.muni.redhat.hiperex.model;

// Generated Dec 25, 2012 6:01:06 PM by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

/**
 * CustomerOrder generated by hbm2java
 */
public class CustomerOrder implements java.io.Serializable {

	private int orderId;
	private Customer customer;
	private Set itemLists = new HashSet(0);

	public CustomerOrder() {
	}

	public CustomerOrder(int orderId) {
		this.orderId = orderId;
	}

	public CustomerOrder(int orderId, Customer customer, Set itemLists) {
		this.orderId = orderId;
		this.customer = customer;
		this.itemLists = itemLists;
	}

	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Set getItemLists() {
		return this.itemLists;
	}

	public void setItemLists(Set itemLists) {
		this.itemLists = itemLists;
	}

}
