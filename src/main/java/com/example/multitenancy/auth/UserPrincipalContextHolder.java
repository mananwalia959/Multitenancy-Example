package com.example.multitenancy.auth;

public class UserPrincipalContextHolder {
	private static final ThreadLocal<UserPrincipal> contextHolder =  new ThreadLocal<>();

   public static void setContext(UserPrincipal userPrincipal) {
      contextHolder.set(userPrincipal);
   }

   public static UserPrincipal getContext() {
      return (UserPrincipal) contextHolder.get();
   }

   public static void clearContext() {
      contextHolder.remove();
   }


}
