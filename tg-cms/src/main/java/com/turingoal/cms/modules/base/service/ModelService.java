package com.turingoal.cms.modules.base.service;

import java.util.List;
import com.turingoal.cms.modules.base.domain.Model;
import com.turingoal.cms.modules.base.domain.form.ModelForm;
import com.turingoal.cms.modules.base.domain.query.ModelQuery;

/**
 * 模型Service
 */
public interface ModelService {

    /**
     * 查询全部 模型
     */
    List<Model> findAll(final ModelQuery query);

    /**
     * 通过id得到一个 模型
     */
    Model get(final String id);

    /**
     * 新增 模型
     */
    void add(final ModelForm form);

    /**
     * 修改 模型
     */
    int update(final ModelForm form);

    /**
     * 复制 模型
     */
    void addAndCopy(final ModelForm form);

    /**
     * 根据id删除一个 模型
     */
    int delete(final String id);

    /**
     * 根据类型查询模型
     */
    List<Model> findByType(String type);
}