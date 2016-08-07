package com.earl.carnet.plugin.database.connection;

import java.sql.Connection;

import com.earl.carnet.plugin.database.config.DefaultConfig;

public interface IConnection {

public Connection configBy(DefaultConfig config);

}
