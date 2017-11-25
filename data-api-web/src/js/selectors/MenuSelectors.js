export const getMenus = (state) => state.menu.get('menus');

export const getActiveMenu = (state) => state.menu.get('menus').get(state.menu.get('activeMenu'));