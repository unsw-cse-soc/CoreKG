import * as ActionTypes from '../constants/ActionTypes';

export function changeMenu(menuId) {
    return {
        type: ActionTypes.SWITCH_MENU,
        payload: menuId
    }
}