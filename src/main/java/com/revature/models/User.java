package com.revature.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.revature.models.Address;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User {
	
	public User(int id, String email, String password, String firstname, String lastname) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.firstName = firstname;
		this.lastName = lastname;
	}
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private int id;
    
    @Column(nullable=false)
    private String email;
    
    @Column(nullable=false)
    private String password;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    private byte[] picture;
    
    @OneToOne
    @JoinColumn(name="address_id", referencedColumnName="address_id")
    private Address address;
    
    @OneToOne
    @JoinColumn(name="wishlist_id", referencedColumnName="wishlist_id")
    private Wishlist wishlist;

    @OneToOne
    @JoinColumn(name="cart_id", referencedColumnName="cart_id")
    private Cart cart;
}
