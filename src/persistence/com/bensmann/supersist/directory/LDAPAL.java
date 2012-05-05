/*
 * LDAPAL.java
 *
 * Created on 28. Oktober 2005, 15:29
 *
 */

package com.bensmann.supersist.directory;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/**
 * This class performs queries against directory servers (LDAP such as OpenLDAP,
 * Sun Java System Directory Server, Oracle Internet Directory,
 * Microsoft Active Directory).
 *
 * Interesting attributes for querying User from MS Active Directory:
 * <p>- sn (Surname)
 * <p>- givenName (Firstname)
 * <p>- name (Account name)
 * <p>- displayName (Name that is displayed)
 * <p>- streetAddress (Street)
 * <p>- postalCode (ZIP)
 * <p-> l (City)
 * <p>- st (State)
 * <p>- co (Country)
 * <p>- c (ISO 3316 Country code)
 * <p>- mail (Email address)
 * <p>- telephoneNumber
 * <p>- userPrincipalName
 * <p>- initials
 *
 * $Header$
 * @author Ralf Bensmann
 * @date $Date$
 * @version $Id$
 * @log $Log$
 */
public final class LDAPAL {
    
    /**
     * User to connect to directory server;
     * For Microsoft Active Directory use: username@server.domain.tld
     */
    private String principal;
    
    /**
     * Password for principal
     */
    private String credentials;
    
    /**
     * URL for directory server
     */
    private String providerUrl;
    
    /**
     * Base DN for all operations
     */
    private String baseDN;
    
    /**
     * Hashtable to store connect information for directory server
     */
    private Map env;
    
    /**
     * Context for LDAP queries
     */
    private LdapContext ldapCtx;
    
    /**
     * Creates a new instance of LDAPAL
     * 
     * @param providerUrl
     * @param principal
     * @param credentials
     * @param baseDN
     */
    private LDAPAL(String providerUrl, String principal,
            String credentials, String baseDN) {
        
        this.providerUrl = providerUrl;
        this.principal = principal;
        this.credentials = credentials;
        this.baseDN = baseDN;
        
        // Populate Hashtable with connect information
        env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        // Set security credentials, we are using simple cleartext
        // authentication
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, principal);
        env.put(Context.SECURITY_CREDENTIALS, credentials);
        // Which sirectory server to connect to
        env.put(Context.PROVIDER_URL, providerUrl);
        // Tracing?
        // env.put("com.sun.jndi.ldap.trace.ber", System.out);
        
    }
    
    /**
     * Creates an instance of LDAPAL, tries to connect to the directory
     * server and returns a ready to use LDAPAL instance.
     * 
     * 
     * @param providerUrl
     * @param principal
     * @param credentials
     * @param baseDN
     * @return Ready to use LDAPAL instance
     */
    public static final LDAPAL create(String providerUrl,
            String principal, String credentials, String baseDN)
            throws NamingException {
        
        LDAPAL ldapal =
                new LDAPAL(providerUrl, principal, credentials, baseDN);
        ldapal.connect();
        
        return ldapal;
        
    }
    
    /**
     * Close connection to directory server
     */
    public final void disconnect() {
        
        try {
            ldapCtx.close();
        } catch (NamingException e) {
        }
        
    }
    
    /**
     * Connect to directory server
     * @throws javax.naming.NamingException
     */
    public final void connect() throws NamingException {
        // Create the initial directory context
        ldapCtx = new InitialLdapContext(
                (Hashtable<?, ?>) env, new Control[] {});
    }
    
    /**
     * Return LDAP context object
     * @return
     */
    public LdapContext getLdapCtx() {
        return ldapCtx;
    }

    /**
     *
     * @param searchFilter Specify the LDAP search filter
     * @throws javax.naming.NamingException
     */
    public final NamingEnumeration search(String searchFilter)
    throws NamingException {
        
        // Create the search controls
        SearchControls searchCtls = new SearchControls();
        // Specify that all attributes should be returned
        String returnedAtts[] = null;
        searchCtls.setReturningAttributes(returnedAtts);
        // Specify the search scope
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        
        // Search for objects using the filter and return results
        return ldapCtx.search(baseDN, searchFilter, searchCtls);
        
    }
    
    /**
     *
     * @param answer
     * @throws javax.naming.NamingException
     */
    public final SearchResult[] convertAnswer(NamingEnumeration answer) {
        
        ArrayList searchResults = new ArrayList();
        SearchResult[] array = null;
        
        // Loop through search results and add them to ArrayList 'searchResults'
        while (answer.hasMoreElements()) {
            
            try {
                SearchResult sr = (SearchResult) answer.next();
                searchResults.add(sr);
            } catch (NamingException e) {
            }
            
        }
        
        // Convert ArrayList into SearchResult[]
        Object[] a = searchResults.toArray();
        array = new SearchResult[a.length];
        for (int i = 0; i < a.length; i++) {
            array[i] = (SearchResult) a[i];
        }
        
        return array;
        
    }
    
    /**
     *
     * @param searchResults
     */
    public final void dumpSearchResults(SearchResult[] searchResults) {
        
        for (int i = 0; i < searchResults.length; i++) {
            
            SearchResult sr = searchResults[i];
            
            System.out.println(">>>" + sr.getName());
            Attributes attrs = sr.getAttributes();
            if (attrs != null) {
                
                NamingEnumeration ne = attrs.getIDs();
                while (ne.hasMoreElements()) {
                    
                    try {
                        
                        // bei attrs.getAll():
                        // Attribute a = (Attribute) ne.next();
                        String a = (String) ne.next();
                        // bei attrs.getAll():
                        // System.out.println("attr="+a.getID());
                        System.out.println("-----> " + a + "="
                                + attrs.get(a).get());
                        
                    } catch (NamingException e) {
                        System.out.println("Problem: " + e);
                    }
                    
                }
                
            }
            
        }
        
    }

//    public static void main(String[] args) throws Exception {
//        
//        LDAPAL l =
//                LDAPAL.create(
//                "ldap://pm-dc1",
//                "rb@pmbs.de", "Password1",
//                "DC=pmbs,DC=de");
//        
//        NamingEnumeration answer = l.search("(&(objectClass=user))");
//        l.dumpSearchResults(l.convertAnswer(answer));
//        l.disconnect();
//        
//    }
    
}