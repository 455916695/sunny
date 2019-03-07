package com.ax.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class VerifyCodeController {


    private static final String VERIFY_CODE = "123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static Random random = new Random();

    @RequestMapping("/verify")
    public String verifyImg(HttpServletResponse response,HttpServletRequest request) {
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int width = 110, height = 40;
        String VerifyCode = setVerifyCode(4,request);
        try {
            createImage(width, height, outputStream, VerifyCode);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String setVerifyCode(int CodeSize,HttpServletRequest request) {
        return setVerifyCode(CodeSize, VERIFY_CODE,request);
    }

    public static String setVerifyCode(int codeSize, String source,HttpServletRequest request) {        //从VERIFY_CODE中随机获得一个字符串
        char[] cha = new char[codeSize];
        for (int i = 0; i < codeSize; i++) {
            cha[i] = source.charAt(random.nextInt(VERIFY_CODE.length()));
        }
        String code = String.valueOf(cha);
        //保存进session中
        request.getSession().setAttribute("verifyCode",code);// 验证码参数名
        return code;
    }

    private static void createImage(int width, int height, OutputStream os, String verifyCode) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        g.setColor(Color.GRAY);                //设置背景色
        g.fillRect(0, 0, width, height);

        Color c = getRandColor(200, 250);
        g.setColor(c);
        g.fillRect(0, 2, width, height - 2);

        g.setColor(getRandColor(160, 200));            //设置前景色
        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int x1 = random.nextInt(6) + 1;
            int y1 = random.nextInt(12) + 1;

            g.drawLine(x, y, x + x1 + 40, y + y1 + 20);
        }

        shear(g, width, height, c);            //将背景变形

        g.setColor(getRandColor(100, 160));                //设置字体与颜色
        int fontSize = height - 4;
        Font font = new Font("Algerian", Font.ITALIC, fontSize);
        g.setFont(font);
        char[] target = verifyCode.toCharArray();
        for (int i = 0; i < verifyCode.length(); i++) {    //绘画字字母

            AffineTransform affine = new AffineTransform();
            int x = ((width - 10) / verifyCode.length()) * i + 5;
            int y = height / 2 + fontSize / 2 - 10;
            affine.setToRotation(Math.PI / 4 * random.nextDouble() * (random.nextBoolean() ? 1 : -1), (width / verifyCode.length()) * i + fontSize / 2, height / 2);
            //setToRotation(旋转角度，旋转点x，旋转点坐标y)

            g.setTransform(affine);
            g.drawChars(target, i, 1, x, y);    //要画的字符数组，从第几个开始，每次画几个，坐标x，坐标y
        }

        g.dispose();            //释放系统资源
        ImageIO.write(image, "jpg", os);
    }

    private static Color getRandColor(int x, int y) {
        int r = random.nextInt(y - x) + x;
        int g = random.nextInt(y - x) + x;
        int b = random.nextInt(y - x) + x;

        return new Color(r, g, b);
    }

    private static void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }

    private static void shearX(Graphics g, int w1, int h1, Color color) {
        int period = random.nextInt(2);
        int frames = 1;
        int phase = random.nextInt(2);
        for (int i = 0; i < h1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                    + (6.2831853071795862D * (double) phase)
                    / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            g.setColor(color);
            g.drawLine((int) d, i, 0, i);
            g.drawLine((int) d + w1, i, w1, i);
        }
    }

    private static void shearY(Graphics g, int w1, int h1, Color color) {
        int period = random.nextInt(40) + 10; // 50;
        int frames = 20;
        int phase = 7;
        for (int i = 0; i < w1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                    + (6.2831853071795862D * (double) phase)
                    / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);

            g.setColor(color);
            g.drawLine(i, (int) d, i, 0);
            g.drawLine(i, (int) d + h1, i, h1);
        }
    }
}
