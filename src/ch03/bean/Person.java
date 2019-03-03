package ch03.bean;

import java.beans.*;

/**
 * Artem Voytenko
 * 03.03.2019
 */

public class Person {
	private PropertyChangeSupport propertyChangeSupport;
	private VetoableChangeSupport vetoableChangeSupport;
	private String name;
	private String address;
	private String phoneNumber;

	public Person() {
		propertyChangeSupport = new PropertyChangeSupport(this);
		vetoableChangeSupport = new VetoableChangeSupport(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		String oldAddress = this.address;
		this.address = address;
		propertyChangeSupport.firePropertyChange("address", oldAddress, this.address);
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) throws PropertyVetoException {
		String oldPhoneNumber = this.phoneNumber;
		vetoableChangeSupport.fireVetoableChange("phoneNumber", oldPhoneNumber, phoneNumber);

		this.phoneNumber = phoneNumber;
		propertyChangeSupport.firePropertyChange("phoneNumber", oldPhoneNumber, this.phoneNumber);
	}

	public void addPropertyChangeListener(PropertyChangeListener l) {
		propertyChangeSupport.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		propertyChangeSupport.removePropertyChangeListener(l);
	}

	public PropertyChangeListener[] getPropertyChangeListeners() {
		return propertyChangeSupport.getPropertyChangeListeners();
	}

	public void addVetoableChangeListener(VetoableChangeListener l) {
		vetoableChangeSupport.addVetoableChangeListener(l);
	}

	public void removeVetoableChangeListener(VetoableChangeListener l) {
		vetoableChangeSupport.removeVetoableChangeListener(l);
	}

	public VetoableChangeListener[] getVetoableChangeListeners() {
		return vetoableChangeSupport.getVetoableChangeListeners();
	}
}
