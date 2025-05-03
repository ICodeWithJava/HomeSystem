package me.icwj.homesystem.database;

public record Credentials(String host, int port, String database, String username, String password, int maximumConnectionPoolSize) {}