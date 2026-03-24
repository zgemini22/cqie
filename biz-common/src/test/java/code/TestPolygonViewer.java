package code;

import com.zds.biz.util.CoordinateUtil;
import com.zds.biz.vo.CoordinateVo;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试多边形视图（带网格、比例尺和经纬度刻度）
 */
public class TestPolygonViewer extends JPanel {

    private final List<CoordinateVo> oldPolygon;
    @Setter
    private List<CoordinateVo> newPolygon;

    public TestPolygonViewer(List<CoordinateVo> oldPolygon, List<CoordinateVo> newPolygon) {
        this.oldPolygon = oldPolygon;
        this.newPolygon = newPolygon;
        setPreferredSize(new Dimension(800, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int padding = 60; // 边距，留给刻度文字

        // 计算多边形边界
        double minX = Double.MAX_VALUE, maxX = -Double.MAX_VALUE;
        double minY = Double.MAX_VALUE, maxY = -Double.MAX_VALUE;

        for (CoordinateVo p : oldPolygon) {
            minX = Math.min(minX, p.getLng().doubleValue());
            maxX = Math.max(maxX, p.getLng().doubleValue());
            minY = Math.min(minY, p.getLat().doubleValue());
            maxY = Math.max(maxY, p.getLat().doubleValue());
        }
        for (CoordinateVo p : newPolygon) {
            minX = Math.min(minX, p.getLng().doubleValue());
            maxX = Math.max(maxX, p.getLng().doubleValue());
            minY = Math.min(minY, p.getLat().doubleValue());
            maxY = Math.max(maxY, p.getLat().doubleValue());
        }

        // 计算缩放比例
        double panelWidth = getWidth() - 2 * padding;
        double panelHeight = getHeight() - 2 * padding;
        double scaleX = panelWidth / (maxX - minX);
        double scaleY = panelHeight / (maxY - minY);
        double scale = Math.min(scaleX, scaleY);

        // 偏移量
        double offsetX = padding - minX * scale;
        double offsetY = padding + maxY * scale;

        // 绘制网格和经纬度刻度
        g2.setColor(Color.LIGHT_GRAY);
        int gridCount = 10; // 网格行列数
        FontMetrics fm = g2.getFontMetrics();
        for (int i = 0; i <= gridCount; i++) {
            // 纵向网格线
            double x = padding + i * panelWidth / gridCount;
            g2.drawLine((int) x, padding, (int) x, padding + (int) panelHeight);
            double lon = minX + i * (maxX - minX) / gridCount;
            String lonStr = String.format("%.4f°", lon);
            int strWidth = fm.stringWidth(lonStr);
            g2.setColor(Color.BLACK);
            g2.drawString(lonStr, (int) (x - strWidth / 2.0), padding + (int) panelHeight + fm.getHeight());
            g2.setColor(Color.LIGHT_GRAY);

            // 横向网格线
            double y = padding + i * panelHeight / gridCount;
            g2.drawLine(padding, (int) y, padding + (int) panelWidth, (int) y);
            double lat = maxY - i * (maxY - minY) / gridCount;
            String latStr = String.format("%.4f°", lat);
            int strHeight = fm.getAscent();
            g2.setColor(Color.BLACK);
            g2.drawString(latStr, padding - fm.stringWidth(latStr) - 5, (int) y + strHeight / 2);
            g2.setColor(Color.LIGHT_GRAY);
        }

        // 绘制旧多边形（蓝色）
        g2.setColor(Color.BLUE);
        Path2D oldPath = new Path2D.Double();
        boolean first = true;
        for (CoordinateVo p : oldPolygon) {
            double x = p.getLng().doubleValue() * scale + offsetX;
            double y = -p.getLat().doubleValue() * scale + offsetY;
            if (first) {
                oldPath.moveTo(x, y);
                first = false;
            } else {
                oldPath.lineTo(x, y);
            }
        }
        oldPath.closePath();
        g2.draw(oldPath);

        // 绘制新多边形（红色）
        g2.setColor(Color.RED);
        Path2D newPath = new Path2D.Double();
        first = true;
        for (CoordinateVo p : newPolygon) {
            double x = p.getLng().doubleValue() * scale + offsetX;
            double y = -p.getLat().doubleValue() * scale + offsetY;
            if (first) {
                newPath.moveTo(x, y);
                first = false;
            } else {
                newPath.lineTo(x, y);
            }
        }
        newPath.closePath();
        g2.draw(newPath);

        // 绘制比例尺（左下角）
        g2.setColor(Color.BLACK);
        int rulerLength = 100; // 像素
        double lonRange = maxX - minX;
        double scaleMetersPerPixel = (lonRange * 111_000) / panelWidth; // 粗略：1经度约111km
        double km = scaleMetersPerPixel * rulerLength / 1000.0;
        int x0 = padding;
        int y0 = getHeight() - padding / 2 - 30;
        g2.drawLine(x0, y0, x0 + rulerLength, y0);
        g2.drawLine(x0, y0 - 5, x0, y0 + 5);
        g2.drawLine(x0 + rulerLength, y0 - 5, x0 + rulerLength, y0 + 5);
        g2.drawString(String.format("%.1f km", km), x0, y0 - 7);
    }

    public static void main(String[] args) {
        // 原始多边形
        List<CoordinateVo> oldPoly = new ArrayList<>();
        oldPoly.add(CoordinateVo.builder().lng(BigDecimal.valueOf(106.662497)).lat(BigDecimal.valueOf(29.727584)).build());
        oldPoly.add(CoordinateVo.builder().lng(BigDecimal.valueOf(106.689277)).lat(BigDecimal.valueOf(29.722797)).build());
        oldPoly.add(CoordinateVo.builder().lng(BigDecimal.valueOf(106.675214)).lat(BigDecimal.valueOf(29.704245)).build());
        oldPoly.add(CoordinateVo.builder().lng(BigDecimal.valueOf(106.657709)).lat(BigDecimal.valueOf(29.706340)).build());
        oldPoly.add(CoordinateVo.builder().lng(BigDecimal.valueOf(106.654567)).lat(BigDecimal.valueOf(29.718757)).build());
        oldPoly.add(CoordinateVo.builder().lng(BigDecimal.valueOf(106.662497)).lat(BigDecimal.valueOf(29.727584)).build()); // 首位闭合
        // 新多边形
        List<CoordinateVo> newPoly = CoordinateUtil.updateScale(oldPoly, 1000, 500);

        TestPolygonViewer viewer = new TestPolygonViewer(oldPoly, newPoly);
        JFrame frame = new JFrame("多边形视图");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 使用 BorderLayout，把 viewer 放中间
        frame.setLayout(new BorderLayout());
        frame.add(viewer, BorderLayout.CENTER);
        // 右上角按钮
        JButton regenerateBtn = new JButton("重新随机多边形");
        regenerateBtn.addActionListener(e -> {
            // 点击时更新 newPolygon
            List<CoordinateVo> regenerated = CoordinateUtil.createPolygon(
                    CoordinateVo.builder().lng(BigDecimal.valueOf(106.668511)).lat(BigDecimal.valueOf(29.716311)).build(),
                    200
            );
            viewer.setNewPolygon(regenerated); // 更新数据
            viewer.repaint(); // 触发重绘
        });

        // 放在北边（上方），右对齐
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(regenerateBtn);
        frame.add(topPanel, BorderLayout.NORTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
