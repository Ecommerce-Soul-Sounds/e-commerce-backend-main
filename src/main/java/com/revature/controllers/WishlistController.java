package com.revature.controllers;

import com.revature.annotations.Authorized;
import com.revature.models.User;
import com.revature.models.Wishlist;
import com.revature.models.WishlistItem;
import com.revature.services.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:3000" }, allowCredentials = "true")
public class WishlistController {

	@Autowired
	private WishlistService wishlistService;

	@Authorized
	@GetMapping
	public @ResponseBody Wishlist getUserWishlist(HttpSession session) {
		User loggedInUser = (User) session.getAttribute("user");
		return wishlistService.getWishlistById(loggedInUser.getWishlist().getId());
	}

	@Authorized
	@GetMapping("/items")
	public @ResponseBody List<WishlistItem> getWishlistItems(HttpSession session) {
		User loggedInUser = (User) session.getAttribute("user");
		return wishlistService.getWishlistItemsByWishlistID(loggedInUser.getWishlist().getId());
	}

	@Authorized
	@PostMapping("/add-item")
	public @ResponseBody String addWishlistItem(@RequestBody int productId, HttpSession session) {
		User loggedInUser = (User) session.getAttribute("user");
		wishlistService.updateWishlist(loggedInUser.getWishlist());
		Boolean success = wishlistService.addWishlistItem(loggedInUser.getWishlist(), productId);
		return success ? "Item added to wishlist" : "Something went wrong, item was not added to wishlist." ;
	}

	@Authorized
	@DeleteMapping("/delete-item")
	public @ResponseBody String deleteWishlistItem(@RequestBody int productId, HttpSession session) {
		User loggedInUser = (User) session.getAttribute("user");
		Boolean success = wishlistService.deleteWishlistItem(loggedInUser.getWishlist(), productId);
		return success ? "Item successfully removed from your wishlist." : "Something went wrong, item could not be removed.";
	}
}
