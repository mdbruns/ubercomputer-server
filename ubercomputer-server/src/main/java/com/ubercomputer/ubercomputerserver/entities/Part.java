package com.ubercomputer.ubercomputerserver.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;
import com.ubercomputer.ubercomputerserver.json.View;

@Entity
@Table(name = "parts")
public class Part {
	public static final int INTEL = 0;
	public static final int NVIDIA = 0;
	public static final int AMD = 1;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@JsonView(View.OrdersByUserID.class)
	private int id;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "name")
	@JsonView(View.OrdersByUserID.class)
	private String name;
	
	@Column(name = "retail_price")
	@JsonView(View.OrdersByUserID.class)
	private int price;
	
	@Column(name = "image_path")
	private String imagePath;
	
	@Column(name = "crossfire_compatible")
	private Boolean crossfireCompatible;
	
	@Column(name = "sli_compatible")
	private Boolean sliCompatible;
	
	@Column(name = "chipset")
	private Integer chipset;
	
	@Column(name = "gpu_brand")
	private Integer gpuBrand;
	
	public Part() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Boolean getCrossfireCompatible() {
		return crossfireCompatible;
	}

	public void setCrossfireCompatible(boolean crossfireCompatible) {
		this.crossfireCompatible = crossfireCompatible;
	}

	public Boolean getSliCompatible() {
		return sliCompatible;
	}

	public void setSliCompatible(boolean sliCompatible) {
		this.sliCompatible = sliCompatible;
	}

	public Integer getChipset() {
		return chipset;
	}

	public void setChipset(int chipset) {
		this.chipset = chipset;
	}

	public Integer getGpuBrand() {
		return gpuBrand;
	}

	public void setGpuBrand(int gpuBrand) {
		this.gpuBrand = gpuBrand;
	}

	@Override
	public String toString() {
		int markedUpPrice = price;
		
		if (type != "operating system") {
			markedUpPrice = (int) Math.round(markedUpPrice * 1.05); 
		}
		
		return name + " (retail: $" + price + " / markup: $" + markedUpPrice + ")";
	}
}