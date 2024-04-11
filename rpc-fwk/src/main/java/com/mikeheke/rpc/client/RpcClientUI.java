package com.mikeheke.rpc.client;

import com.mikeheke.rpc.client.proxy.RemoteServiceProxyFactory;
import com.mikeheke.rpc.common.business.IUserService;
import com.mikeheke.rpc.common.business.dto.UserDTO;
import com.mikeheke.rpc.common.business.vo.UserVO;
import io.netty.channel.Channel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * RPC客户端UI界面
 *
 * @author heke
 * @since 2024-04-03
 */
@Slf4j
@Data
public class RpcClientUI implements ActionListener {
    private JFrame jFrame = new JFrame();
    private JTextArea areaShow = new JTextArea(14, 35);
    private JTextArea areaTip = new JTextArea(5, 35);
    private JTextArea areaInput = new JTextArea(3, 35);
    private Channel channel;

    private void initAreaTip() {
        areaTip.append("【command】\n");
        areaTip.append("0:findByAccount  [account]\n");
        areaTip.append("1:listUser  [account]\n");
    }

    private void send(String msg) {
        IUserService userService = (IUserService) RemoteServiceProxyFactory.getProxyServiceInstance(IUserService.class);
        String command = msg.split(" ")[0];
        if ( "0".equals(command) ) {
            areaShow.append("服务端正在处理中......\n");
            UserVO userVO = userService.findByAccount(msg.split(" ")[1]);
            areaShow.append("服务端返回：\n");
            if ( userVO != null ) {
                areaShow.append(userVO.toString());
            } else {
                areaShow.append("null");
            }
            areaShow.append("\n");
        } else if ( "1".equals(command) ) {
            UserDTO userDTO = new UserDTO();
            userDTO.setAccount(msg.split(" ")[1]);
            java.util.List<UserVO> userVOList = userService.listUser(userDTO);
            areaShow.append("服务端返回：\n");
            areaShow.append(userVOList.toString());
            areaShow.append("\n");
        }
    }

    public void initUI() {
        Color backgroundColor = new Color(43,43,43);
        Color foregroundColor = new Color(159, 177, 193);
        Font font = new Font("Serif", Font.PLAIN, 15);

        jFrame.setTitle("rpc-client");
        jFrame.setSize(420, 530);
        jFrame.getContentPane().setBackground(new Color(60,63,65));

        areaShow.setBackground(backgroundColor);
        areaShow.setForeground(foregroundColor);
        areaTip.setBackground(backgroundColor);
        areaTip.setForeground(foregroundColor);
        areaInput.setBackground(backgroundColor);
        areaInput.setForeground(foregroundColor);
        areaInput.setCaretColor(foregroundColor);

        areaShow.setFont(font);
        areaTip.setFont(font);
        areaInput.setFont(new Font("Serif", Font.PLAIN, 15));

        // 创建一个JScrollPane，并将JTextArea添加到其中
        JScrollPane scrollPane = new JScrollPane(areaShow);

        FlowLayout flowLayout = new FlowLayout();
        jFrame.setLayout(flowLayout);
        areaShow.setEditable(false);
        areaShow.setLineWrap(true); // 启用自动换行
        areaShow.setWrapStyleWord(true); // 设置为真，以在单词边界处换行
        areaTip.setEditable(false);
        jFrame.add(scrollPane);
        jFrame.add(areaTip);
        jFrame.add(areaInput);

        scrollPane.setBackground(backgroundColor);
        scrollPane.setForeground(foregroundColor);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JButton sendBtn = new JButton("send");
        sendBtn.setBackground(backgroundColor);
        sendBtn.setForeground(foregroundColor);
        jFrame.add(sendBtn);
        jFrame.setDefaultCloseOperation(3);
        jFrame.setVisible(true);
        sendBtn.addActionListener(this);

        initAreaTip();

        // 输入框 监听回车事件
        areaInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    String msg = areaInput.getText().trim();
                    areaShow.append(msg + "\n");
                    areaInput.setText("");
                    new Thread(() -> send(msg)).start();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) { }

            @Override
            public void keyReleased(KeyEvent e) { }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = areaInput.getText().trim();
        areaShow.append(msg + "\n");
        areaInput.setText("");
        new Thread(() -> send(msg)).start();
    }

}
