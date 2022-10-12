package com.revature.controllers;

import com.revature.annotations.Authorized;
import com.revature.models.ClientMessage;
import com.revature.models.User;
import com.revature.models.Wishlist;
import com.revature.models.WishlistItem;

import com.revature.services.WishlistService;
import com.revature.util.ClientMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@CrossOrigin(origins = {"http://soulsounds.s3-website-us-west-2.amazonaws.com"}, allowCredentials = "true")
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
		return wishlistService.getWishlistItemsByWishlist(loggedInUser.getWishlist());
	}

	@Authorized
	@PostMapping("/add-item")
	public @ResponseBody ClientMessage addWishlistItem(@RequestBody int productId, HttpSession session) {
		User loggedInUser = (User) session.getAttribute("user");
		wishlistService.updateWishlist(loggedInUser.getWishlist());
		boolean success = wishlistService.addWishlistItem(loggedInUser.getWishlist(), productId);
		return success ? ClientMessageUtil.WISHLIST_ITEM_ADDITION_SUCCESSFUL : ClientMessageUtil.WISHLIST_ITEM_ADDITION_FAILED ;
	}

	@Authorized
	@DeleteMapping("/delete-item")
	public @ResponseBody String deleteWishlistItem(@RequestParam int productId, HttpSession session) {
		User loggedInUser = (User) session.getAttribute("user");
		boolean success = wishlistService.deleteWishlistItem(loggedInUser.getWishlist(), productId);
		return success ? "Item successfully removed from your wishlist." : "Something went wrong, item could not be removed.";
	}
}
