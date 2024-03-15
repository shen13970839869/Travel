package com.zte.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.zte.dao.CategoryDao;
import com.zte.dao.CategoryDaoImpl;
import com.zte.pojo.Category;
import com.zte.utils.JedisUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        //1.��redis�в�ѯ
        //1.1��ȡjedis�ͻ���
        Jedis jedis = JedisUtil.getJedis();
        //1.2��ʹ��sortedset�����ѯ
        //Set<String> categorys = jedis.zrange("category", 0, -1);
        //1.3��ѯsortedset�еķ���(cid)��ֵ(cname)
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);

        List<Category> cs = null;
        //2.�жϲ�ѯ�ļ����Ƿ�Ϊ��
        if (categorys == null || categorys.size() == 0) {

            System.out.println("�����ݿ��ѯ....");
            //3.���Ϊ��,��Ҫ�����ݿ��ѯ,�ڽ����ݴ���redis
            //3.1 �����ݿ��ѯ
            cs = categoryDao.findAll();
            //3.2 ���������ݴ洢��redis�е� category��key
            for (int i = 0; i < cs.size(); i++) {

                jedis.zadd("category", cs.get(i).getCid(), cs.get(i).getCname());
            }
        } else {
            System.out.println("��redis�в�ѯ.....");

            //4.�����Ϊ��,��set�����ݴ���list
            cs = new ArrayList<Category>();
            for (Tuple tuple : categorys) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int)tuple.getScore());
                cs.add(category);

            }
        }
        

        return cs;
    }

}
