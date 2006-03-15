/**
 * 
 */
package tiled.mapeditor.actions;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import tiled.core.Map;
import tiled.core.MapLayer;
import tiled.core.StatefulTile;
import tiled.core.Tile;
import tiled.core.TileGroup;
import tiled.core.TileLayer;
import tiled.mapeditor.MapEditor;

/**
 * @author mtotz
 *
 */
public class CreateMultiLayerBrushAction extends AbstractAction
{
  private static final long serialVersionUID = -8004754730959503398L;

  private MapEditor mapEditor;
  
  public CreateMultiLayerBrushAction(MapEditor mapEditor)
  {
    super("");
    putValue(SHORT_DESCRIPTION, "Creates a new multilayer brush using all visible layers");
    putValue(SMALL_ICON, MapEditor.loadIcon("resources/plus2.png"));
    this.mapEditor = mapEditor;
  }

  public void actionPerformed(ActionEvent e)
  {
    List<Point> selectedList = new ArrayList<Point>(mapEditor.getSelectedTiles());
    List<StatefulTile> brushList = new ArrayList<StatefulTile>();
    
    Map map = mapEditor.currentMap;

    // get all layers
    for (int i = 0; i < map.getTotalLayers(); i++)
    {
      MapLayer mapLayer = map.getLayer(i);
      if (mapLayer.isVisible() && mapLayer instanceof TileLayer)
      {
        TileLayer tileLayer = (TileLayer) mapLayer;
        // copy tiles
        for (Point p : selectedList)
        {
          Tile tile = tileLayer.getTileAt(p.x, p.y);
          if (tile != null)
          {
            brushList.add(new StatefulTile(p,i,tile));
          }
        }
      }
    }

    if (brushList.size() > 0)
    {
      String s = (String) JOptionPane.showInputDialog(
          mapEditor.appFrame,
          "Enter a name for the brush:",
          "Brush Name",
          JOptionPane.PLAIN_MESSAGE,
          null,
          null,
          "name");

      TileGroup tileGroup = new TileGroup(map,brushList,s == null ? "unnamed" : s);
      map.addUserBrush(tileGroup.normalize());
      mapEditor.layerEditPanel.repaint();
    }
  }

}
