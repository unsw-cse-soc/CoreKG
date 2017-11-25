import Immutable from 'immutable';

const Menu = Immutable.Record({
    id: '',
    title: '',
    url: '',
    children: null,
    isRoot: true
});

export default Menu;