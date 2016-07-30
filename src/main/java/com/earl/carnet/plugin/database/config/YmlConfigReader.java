package com.earl.carnet.plugin.database.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

import org.yaml.snakeyaml.Yaml;

public class YmlConfigReader implements IConfigReader{

	public static DefaultConfig config;
	
	/**
	 * 依赖snakeyaml 和 gson 实现数据读取
	 */
	@Override
	public DefaultConfig readResource(String resource)  {
		// TODO Auto-generated method stub
		Yaml yaml = new Yaml();
		URL url = YmlConfigReader.class.getClassLoader().getResource(resource);
		if (url != null) {
				try {
					
					config = yaml.loadAs(new FileInputStream(url.getFile()),DefaultConfig.class);
					
					System.out.println(config);
					return config;
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		throw new RuntimeException("不存在该资源文件");
	}

	public static void main(String[] args) {
		new YmlConfigReader().readResource("backup/backup-config.yml");
	}
	
	@Override
	public IConfig readResource(InputStream inputStream) {
		// TODO Auto-generated method stub
		
		
		return null;
	}

	
	
}
