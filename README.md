# bomb
踩地雷遊戲


## main()

in gamesetup.java

Bombgame.startgemae()

## startgemae()

設定遊戲介面

呼叫gamestarttable載入遊戲畫面


## gamestarttable
偵測滑鼠點擊

if(踩到地雷)

結束遊戲

else

針對點擊到的點做BFS搜尋鄰近點計算

起始點數字為零

周圍有地雷就++

若搜尋完仍為零就像周圍擴散

並加入待搜尋的Queue中

直到Queue裡沒東西
