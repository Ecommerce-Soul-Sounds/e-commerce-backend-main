package com.revature.util;

import com.revature.models.ClientMessage;

public class ClientMessageUtil {
	private ClientMessageUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static final ClientMessage CREATION_SUCCESSFUL = new ClientMessage("CREATION SUCCESSFUL");
	public static final ClientMessage CREATION_FAILED = new ClientMessage("SOMETHING WENT WRONG DURING CREATION");
	public static final ClientMessage ORDER_SUBMISSION_SUCCESSFUL = new ClientMessage("ORDER PLACED SUCCESSFULLY");
	public static final ClientMessage ORDER_SUBMISSION_FAILED = new ClientMessage("ORDER COULD NOT BE PLACED AT THIS TIME. PLEASE TRY AGAIN.");
	public static final ClientMessage ADD_CART_ITEM_SUCCESSFUL = new ClientMessage("ITEM ADDED TO CART.");
	public static final ClientMessage ADD_CART_ITEM_FAILED = new ClientMessage("ITEM COULD NOT BE ADDED TO CART. PLEASE TRY AGAIN.");
	public static final ClientMessage UPDATE_SUCCESSFUL = new ClientMessage("UPDATE SUCCESSFUL");
	public static final ClientMessage UPDATE_FAILED = new ClientMessage("SOMETHING WENT WRONG DURING UPDATE");
	public static final ClientMessage DELETION_SUCCESSFUL = new ClientMessage("DELETION SUCCESSFUL");
	public static final ClientMessage DELETION_FAILED = new ClientMessage("SOMETHING WENT WRONG DURING DELETION");
	public static final ClientMessage ENTITY_ALREADY_EXISTS = new ClientMessage("AN ENTITY MATCHING THIS ONE ALREADY EXISTS.");
	public static final ClientMessage EMAIL_ALREADY_EXISTS = new ClientMessage("THAT EMAIL IS ALREADY TAKEN");
	public static final ClientMessage WISHLIST_ITEM_ADDITION_SUCCESSFUL = new ClientMessage("ITEM SUCCESSFULLY ADDED");
	public static final ClientMessage WISHLIST_ITEM_ADDITION_FAILED = new ClientMessage("ITEM COULD NOT BE ADDED. PLEASE TRY AGAIN.");
	public static final ClientMessage WISHLIST_ITEM_DELETION_SUCCESSFUL = new ClientMessage("ITEM SUCCESSFULLY REMOVED FROM YOUR WISHLIST");
	public static final ClientMessage WISHLIST_ITEM_DELETION_FAILED = new ClientMessage("ITEM COULD NOT BE DELETED PLEASE TRY AGAIN.");

}
