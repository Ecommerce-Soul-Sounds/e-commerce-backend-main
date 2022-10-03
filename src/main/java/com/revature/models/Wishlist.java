package com.revature.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="wishlist")
public class Wishlist {	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="wishlist_id")
	private int id;
	
	private LocalDate dateModified;
}
