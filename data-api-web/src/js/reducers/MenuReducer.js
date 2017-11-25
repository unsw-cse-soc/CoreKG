import Immutable from 'immutable';
import * as ActionTypes from '../constants/ActionTypes';
import Menu from '../models/Menu';
import MenuMap from '../models/MenuMap';

export default function reducer(state = Immutable.Map({
    menus: new MenuMap({
        'installation': new Menu({
            id: 'installation',
            title: 'Installation',
            url: '/installation'
        }),
        'database': new Menu({
            id: 'database',
            title: 'Database',
            url: '/database'
        }),
        'entity': new Menu({
            id: 'entity',
            title: 'Entity',
            url: '/entity'
        }),
        'relations': new Menu({
            id: 'relations',
            title: 'Relations',
            url: '/relations'
        }),
        'query': new Menu({
            id: 'query',
            title: 'Query',
            url: '/query'
        })
    }),
    activeMenu: 'installation',
    error: null,
}), action) {
    switch (action.type) {
        case ActionTypes.SWITCH_MENU: {
            const menuId = action.payload;
            if (state.hasIn(['menus', menuId])) {
                return state.withMutations(map => {
                    map.set('error', null)
                        .set('activeMenu', menuId)
                });
            } else {
                return state.set('error', 'Invalid menu');
            }
        }
    }
    return state;
}