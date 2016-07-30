package com.earl.carnet.plugin.database.stroge;

import com.earl.carnet.plugin.database.config.IConfig;

public interface StrogeFactory {

	IStroge getStroge(IConfig config);

}
