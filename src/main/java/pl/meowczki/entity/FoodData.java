package pl.meowczki.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
@JsonSerialize 
@Entity
public class FoodData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getKcals() {
		return kcals;
	}

	public void setKcals(double kcals) {
		this.kcals = kcals;
	}

	public double getCarb() {
		return carb;
	}

	public void setCarb(double carb) {
		this.carb = carb;
	}

	public double getProt() {
		return prot;
	}

	public void setProt(double prot) {
		this.prot = prot;
	}

	public double getFat() {
		return fat;
	}

	public void setFat(double fat) {
		this.fat = fat;
	}

	public double getFib() {
		return fib;
	}

	public void setFib(double fib) {
		this.fib = fib;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@NotNull
	private String name;
	@NotNull
	private double kcals;
	@NotNull
	private double carb;
	@NotNull
	private double prot;
	@NotNull
	private double fat;

	@Override
	public String toString() {
		return "FoodData [id=" + id + ", Name=" + name + ", kcals=" + kcals + ", carb=" + carb + ", prot=" + prot
				+ ", fat=" + fat + ", fib=" + fib + ", weight=" +   "]";
	}

	@NotNull
	private double fib;
	@Transient
	private double weight;
}
