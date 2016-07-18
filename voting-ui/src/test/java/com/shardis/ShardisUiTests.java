package com.shardis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.prodyna.pac.VotingUiApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VotingUiApplication.class)
@WebAppConfiguration
public class ShardisUiTests {

  @Test
  public void contextLoads() {
  }

}
