package fi.muni.redhat.hiperex.model;

// Generated Dec 25, 2012 6:01:06 PM by Hibernate Tools 4.0.0

/**
 * ItemListId generated by hbm2java
 */
public class ItemListId implements java.io.Serializable {

	private int orderId;
	private int productId;

	public ItemListId() {
	}

	public ItemListId(int orderId, int productId) {
		this.orderId = orderId;
		this.productId = productId;
	}

	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getProductId() {
		return this.productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ItemListId))
			return false;
		ItemListId castOther = (ItemListId) other;

		return (this.getOrderId() == castOther.getOrderId()) && (this.getProductId() == castOther.getProductId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getOrderId();
		result = 37 * result + this.getProductId();
		return result;
	}

}
