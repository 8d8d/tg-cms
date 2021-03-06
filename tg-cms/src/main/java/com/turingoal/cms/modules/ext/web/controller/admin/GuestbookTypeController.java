package com.turingoal.cms.modules.ext.web.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.github.pagehelper.Page;
import com.turingoal.cms.modules.ext.domain.GuestbookType;
import com.turingoal.cms.modules.ext.domain.form.GuestbookTypeForm;
import com.turingoal.cms.modules.ext.domain.query.GuestbookTypeQuery;
import com.turingoal.cms.modules.ext.service.GuestbookTypeService;
import com.turingoal.common.bean.JsonResultBean;
import com.turingoal.common.bean.PageGridBean;
import com.turingoal.common.constants.ConstantPattern4Date;
import com.turingoal.common.exception.BusinessException;
import com.turingoal.common.support.validator.ValidGroupAdd;
import com.turingoal.common.support.validator.ValidGroupUpdate;

/**
 * 留言板类型Controller
 */
@Controller
@RequestMapping("/m/ext/guestbookType")
public class GuestbookTypeController {

    private static final String LIST_PAGE = "modules/site/guestbookType/list";
    private static final String ADD_PAGE = "modules/site/guestbookType/add";
    private static final String EDIT_PAGE = "modules/site/guestbookType/edit";

    @Autowired
    private GuestbookTypeService guestbookTypeService;

    /**
     * 留言板类型信息查询界面
     */
    @RequestMapping(value = "/list.gsp", method = RequestMethod.GET)
    public ModelAndView listPage(final GuestbookTypeQuery query) throws BusinessException {
        ModelAndView mav = new ModelAndView(LIST_PAGE);
        Page<GuestbookType> result = guestbookTypeService.findAll(query);
        mav.addObject("guestbookTypeList", new PageGridBean(query, result, true));
        return mav;
    }

    /**
     * 留言板类型查询信息
     */
    @RequestMapping(value = "/list.gsp", method = RequestMethod.POST)
    @ResponseBody
    public PageGridBean list(final GuestbookTypeQuery query) throws BusinessException {
        Page<GuestbookType> result = guestbookTypeService.findAll(query);
        return new PageGridBean(query, result, true);
    }

    /**
     * 通过id得到一个留言板类型
     */
    @RequestMapping(value = "/get.gsp")
    @ResponseBody
    public Object get(@RequestParam("id") final String id) throws BusinessException {
        return guestbookTypeService.get(id);
    }

    /**
     * 检查名称是否重复
     */
    @RequestMapping(value = "/checkTypeName.gsp", method = RequestMethod.POST)
    @ResponseBody
    public final boolean checkTypeName(@ModelAttribute("typeName") final String typeName, @ModelAttribute("id") final String id) throws BusinessException {
        GuestbookType guestbookType = guestbookTypeService.getByTypeName(typeName);
        return !(guestbookType != null && !id.equals(guestbookType.getId()));
    }

    /**
     * 检查编码是否重复
     */
    @RequestMapping(value = "/checkCodeNum.gsp", method = RequestMethod.POST)
    @ResponseBody
    public final boolean checkCodeNum(@ModelAttribute("codeNum") final String codeNum, @ModelAttribute("id") final String id) throws BusinessException {
        GuestbookType guestbookType = guestbookTypeService.getByCodeNum(codeNum);
        return !(guestbookType != null && !id.equals(guestbookType.getId()));
    }

    /**
     * 新增界面
     */
    @RequestMapping(value = "/add.gsp", method = RequestMethod.GET)
    public String addPage() {
        return ADD_PAGE;
    }

    /**
     * 新增留言板类型
     */
    @RequestMapping(value = "/add.gsp", method = RequestMethod.POST)
    public final String add(@Validated({ ValidGroupAdd.class }) @ModelAttribute("form") final GuestbookTypeForm form, final BindingResult bindingResult) throws BusinessException {
        /*
         * // 数据校验 if (bindingResult.hasErrors()) { String errorMsg = SpringBindingResultWrapper.warpErrors(bindingResult); return new JsonResultBean(JsonResultBean.FAULT, errorMsg); } else { guestbookTypeService.add(form); return new JsonResultBean(JsonResultBean.SUCCESS); }
         */
        guestbookTypeService.add(form);
        return "redirect:/admin/m/ext/guestbookType/list.gsp";
    }

    /**
     * 修改界面
     */
    @RequestMapping(value = "/edit_{id}.gsp", method = RequestMethod.GET)
    public ModelAndView editPage(@PathVariable final String id) {
        ModelAndView mav = new ModelAndView(EDIT_PAGE);
        mav.addObject("result", guestbookTypeService.get(id));
        return mav;
    }

    /**
     * 修改留言板类型
     */
    @RequestMapping(value = "/edit.gsp", method = RequestMethod.POST)
    public final String edit(@Validated({ ValidGroupUpdate.class }) @ModelAttribute("form") final GuestbookTypeForm form, final BindingResult bindingResult) throws BusinessException {
        /*
         * // 数据校验 if (bindingResult.hasErrors()) { String errorMsg = SpringBindingResultWrapper.warpErrors(bindingResult); return new JsonResultBean(JsonResultBean.FAULT, errorMsg); } else { guestbookTypeService.update(form); return new JsonResultBean(JsonResultBean.SUCCESS); }
         */
        guestbookTypeService.update(form);
        return "redirect:/admin/m/ext/guestbookType/list.gsp";
    }

    /**
     * 根据id删除留言板类型
     */
    @RequestMapping(value = "/delete_{id}.gsp", method = RequestMethod.POST)
    @ResponseBody
    public final JsonResultBean delete(@PathVariable("id") final String id) throws BusinessException {
        guestbookTypeService.delete(id);
        return new JsonResultBean(JsonResultBean.SUCCESS);
    }

    /**
     * 根据id启用留言板类型
     */
    @RequestMapping(value = "/enable_{id}.gsp", method = RequestMethod.POST)
    @ResponseBody
    public final JsonResultBean enable(@PathVariable("id") final String id) throws BusinessException {
        guestbookTypeService.enable(id);
        return new JsonResultBean(JsonResultBean.SUCCESS);
    }

    /**
     * 根据id停用留言板类型
     */
    @RequestMapping(value = "/disable_{id}.gsp", method = RequestMethod.POST)
    @ResponseBody
    public final JsonResultBean disable(@PathVariable("id") final String id) throws BusinessException {
        guestbookTypeService.disable(id);
        return new JsonResultBean(JsonResultBean.SUCCESS);
    }

    /**
     * 将form表单里面的String Date转换成Date型，字符串去掉空白
     */
    @InitBinder
    protected final void initBinder(final HttpServletRequest request, final ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(ConstantPattern4Date.YYYY_MM_DD), true));
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}