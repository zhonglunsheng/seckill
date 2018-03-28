package com.seckill.controller;

import com.seckill.common.ServerResponse;
import com.seckill.redis.GoodsKey;
import com.seckill.redis.RedisService;
import com.seckill.service.IGoodsService;
import com.seckill.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    IUserService iUserService;

    @Autowired
    RedisService redisService;

    @Autowired
    IGoodsService iGoodsService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    /**
     * 获取商品列表
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/to_list",produces = "text/html")
    @ResponseBody
    public String list(Model model, HttpServletRequest request, HttpServletResponse response) {
        String html = redisService.get(GoodsKey.goodsList,"", String.class);
        if (StringUtils.isBlank(html)){
            ServerResponse result = iGoodsService.listGoodsVo();
            model.addAttribute("goodsList", result.getData());
            //手动渲染
            WebContext webContext = new WebContext(request,response,request.getServletContext(),response.getLocale(),model.asMap());
            html = thymeleafViewResolver.getTemplateEngine().process("goods_list",webContext);
            redisService.set(GoodsKey.goodsList,"",html);
        }
        return html;
    }

    @RequestMapping(value = "/to_detail/{goodsId}")
    @ResponseBody
    public ServerResponse detail(@PathVariable("goodsId")long goodsId) {
        return iGoodsService.getGoodsVoByGoodsId(goodsId);
    }
}
