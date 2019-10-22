package com.ubercomputer.ubercomputerserver.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;
import com.ubercomputer.ubercomputerserver.json.View;

@Entity
@Table(name = "orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@JsonView(View.OrdersByUserID.class)
	private int id;
	
	@Column(name = "express_service")
	private boolean expressService;
	
	@Column(name = "is_crossfire_sli_system")
	@JsonView(View.OrdersByUserID.class)
	private boolean isCrossfireSLISystem;
	
	@Column(name = "subtotal")
	private int subtotal;
	
	@Column(name = "building_fee")
	private int buildingFee;
	
	@Column(name = "service_fee")
	private int serviceFee;
	
	@Column(name = "grand_total")
	@JsonView(View.OrdersByUserID.class)
	private int grandTotal;
	
	@Column(name = "date_time_submitted")
	@JsonView(View.OrdersByUserID.class)
	private Timestamp dateTimeSubmitted;
	
	@Column(name="deadline_for_ready_for_delivery")
	@JsonView(View.OrdersByUserID.class)
	private Timestamp deadlineForReadyForDelivery;
	
	@Column(name = "status")
	@JsonView(View.OrdersByUserID.class)
	private String status;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
    @JoinColumn(name = "operating_system_id")
	@JsonView(View.OrdersByUserID.class)
	private Part operatingSystem;
	
	@ManyToOne
    @JoinColumn(name = "case_id")
	@JsonView(View.OrdersByUserID.class)
	private Part computerCase;
	
	@ManyToOne
    @JoinColumn(name = "motherboard_id")
	@JsonView(View.OrdersByUserID.class)
	private Part motherboard;
	
	@ManyToOne
    @JoinColumn(name = "processor_id")
	@JsonView(View.OrdersByUserID.class)
	private Part processor;
	
	@ManyToOne
    @JoinColumn(name = "processor_cooler_id")
	@JsonView(View.OrdersByUserID.class)
	private Part processorCooler;
	
	@ManyToOne
    @JoinColumn(name = "power_supply_id")
	@JsonView(View.OrdersByUserID.class)
	private Part powerSupply;
	
	@ManyToOne
    @JoinColumn(name = "video_card_id")
	@JsonView(View.OrdersByUserID.class)
	private Part videoCard;
	
	@ManyToOne
    @JoinColumn(name = "memory_id")
	@JsonView(View.OrdersByUserID.class)
	private Part memory;
	
	@ManyToOne
    @JoinColumn(name = "primary_hard_drive_id")
	@JsonView(View.OrdersByUserID.class)
	private Part primaryHardDrive;
	
	@ManyToOne
    @JoinColumn(name = "secondary_hard_drive_id")
	@JsonView(View.OrdersByUserID.class)
	private Part secondaryHardDrive;
	
	@ManyToOne
    @JoinColumn(name = "case_fan_id")
	@JsonView(View.OrdersByUserID.class)
	private Part caseFan;
	
	@ManyToOne
    @JoinColumn(name = "lighting_id")
	@JsonView(View.OrdersByUserID.class)
	private Part lighting;
	
	@ManyToOne
    @JoinColumn(name = "optical_drive_id")
	@JsonView(View.OrdersByUserID.class)
	private Part opticalDrive;
	
	@ManyToOne
    @JoinColumn(name = "wifi_adapter_id")
	@JsonView(View.OrdersByUserID.class)
	private Part wifiAdapter;
	
	@ManyToOne
    @JoinColumn(name = "sound_card_id")
	@JsonView(View.OrdersByUserID.class)
	private Part soundCard;
	
	@ManyToOne
    @JoinColumn(name = "monitor_id")
	@JsonView(View.OrdersByUserID.class)
	private Part monitor;
	
	@ManyToOne
    @JoinColumn(name = "keyboard_id")
	@JsonView(View.OrdersByUserID.class)
	private Part keyboard;
	
	@ManyToOne
    @JoinColumn(name = "mouse_id")
	@JsonView(View.OrdersByUserID.class)
	private Part mouse;
	
	public Order() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isExpressService() {
		return expressService;
	}

	public void setExpressService(boolean expressService) {
		this.expressService = expressService;
	}

	public boolean isCrossfireSLISystem() {
		return isCrossfireSLISystem;
	}

	public void setCrossfireSLISystem(boolean isCrossfireSLISystem) {
		this.isCrossfireSLISystem = isCrossfireSLISystem;
	}

	public int getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(int subtotal) {
		this.subtotal = subtotal;
	}

	public int getBuildingFee() {
		return buildingFee;
	}

	public void setBuildingFee(int buildingFee) {
		this.buildingFee = buildingFee;
	}

	public int getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(int serviceFee) {
		this.serviceFee = serviceFee;
	}

	public int getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(int grandTotal) {
		this.grandTotal = grandTotal;
	}

	public Timestamp getDateTimeSubmitted() {
		return dateTimeSubmitted;
	}

	public void setDateTimeSubmitted(Timestamp dateTimeSubmitted) {
		this.dateTimeSubmitted = dateTimeSubmitted;
	}

	public Timestamp getDeadlineForReadyForDelivery() {
		return deadlineForReadyForDelivery;
	}

	public void setDeadlineForReadyForDelivery(Timestamp deadlineForReadyForDelivery) {
		this.deadlineForReadyForDelivery = deadlineForReadyForDelivery;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Part getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(Part operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public Part getComputerCase() {
		return computerCase;
	}

	public void setComputerCase(Part computerCase) {
		this.computerCase = computerCase;
	}

	public Part getMotherboard() {
		return motherboard;
	}

	public void setMotherboard(Part motherboard) {
		this.motherboard = motherboard;
	}

	public Part getProcessor() {
		return processor;
	}

	public void setProcessor(Part processor) {
		this.processor = processor;
	}

	public Part getProcessorCooler() {
		return processorCooler;
	}

	public void setProcessorCooler(Part processorCooler) {
		this.processorCooler = processorCooler;
	}

	public Part getPowerSupply() {
		return powerSupply;
	}

	public void setPowerSupply(Part powerSupply) {
		this.powerSupply = powerSupply;
	}

	public Part getVideoCard() {
		return videoCard;
	}

	public void setVideoCard(Part videoCard) {
		this.videoCard = videoCard;
	}

	public Part getMemory() {
		return memory;
	}

	public void setMemory(Part memory) {
		this.memory = memory;
	}

	public Part getPrimaryHardDrive() {
		return primaryHardDrive;
	}

	public void setPrimaryHardDrive(Part primaryHardDrive) {
		this.primaryHardDrive = primaryHardDrive;
	}

	public Part getSecondaryHardDrive() {
		return secondaryHardDrive;
	}

	public void setSecondaryHardDrive(Part secondaryHardDrive) {
		this.secondaryHardDrive = secondaryHardDrive;
	}

	public Part getCaseFan() {
		return caseFan;
	}

	public void setCaseFan(Part caseFan) {
		this.caseFan = caseFan;
	}

	public Part getLighting() {
		return lighting;
	}

	public void setLighting(Part lighting) {
		this.lighting = lighting;
	}

	public Part getOpticalDrive() {
		return opticalDrive;
	}

	public void setOpticalDrive(Part opticalDrive) {
		this.opticalDrive = opticalDrive;
	}

	public Part getWifiAdapter() {
		return wifiAdapter;
	}

	public void setWifiAdapter(Part wifiAdapter) {
		this.wifiAdapter = wifiAdapter;
	}

	public Part getSoundCard() {
		return soundCard;
	}

	public void setSoundCard(Part soundCard) {
		this.soundCard = soundCard;
	}

	public Part getMonitor() {
		return monitor;
	}

	public void setMonitor(Part monitor) {
		this.monitor = monitor;
	}

	public Part getKeyboard() {
		return keyboard;
	}

	public void setKeyboard(Part keyboard) {
		this.keyboard = keyboard;
	}

	public Part getMouse() {
		return mouse;
	}

	public void setMouse(Part mouse) {
		this.mouse = mouse;
	}

	@Override
	public String toString() {
		return "ORDER DATA\n\n" + "id=" + id + "\nexpressService=" + expressService + "\nsubtotal=" + subtotal + "\nserviceFee="
				+ serviceFee + "\nbuildingFee=" + buildingFee + "\ngrandTotal=" + grandTotal + "\ndateTimeSubmitted=" + dateTimeSubmitted
				+ "\ndeadlineForReadyForDelivery=" + deadlineForReadyForDelivery + "\nstatus=" + status + "\n\nUSER DATA\n\n"
				+ user + "\n\nSELECTEDPARTS" + "\n\noperatingSystem=" + operatingSystem + "\ncomputerCase=" + computerCase + "\nmotherboard="
				+ motherboard + "\nprocessor=" + processor + "\nprocessorCooler=" + processorCooler + "\npowerSupply="
				+ powerSupply + "\nvideoCard=" + videoCard + "\nmemory=" + memory + "\nprimaryHardDrive="
				+ primaryHardDrive + "\nsecondaryHardDrive=" + secondaryHardDrive + "\ncaseFan=" + caseFan
				+ "\nopticalDrive=" + opticalDrive + "\nwifiAdapter=" + wifiAdapter
				+ "\nsoundCard=" + soundCard + "\nmonitor=" + monitor + "\nkeyboard=" + keyboard + "\nmouse=" + mouse;
	}	
}