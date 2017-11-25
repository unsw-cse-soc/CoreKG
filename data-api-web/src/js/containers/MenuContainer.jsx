import React from "react";
import ReactDOM from "react-dom";
import { push } from 'react-router-redux';
import { connect } from "react-redux";
import { Link } from 'react-router';
import { changeMenu } from '../actions/MenuActions';
import { getMenus } from '../selectors/MenuSelectors';
import { getActiveMenu } from '../selectors/MenuSelectors';
import isNil from 'lodash/isNil';

class MenuContainer extends React.Component {

    constructor(props) {
        super(props);
    }

    componentDidMount() {
    }

    componentDidUpdate(prevProps) {
        if (prevProps.acttiveMenu.id !== this.props.acttiveMenu.id) {
            this.props.onNavigateTo(this.props.acttiveMenu.id);
        }
    }

    render() {
        const {menus, acttiveMenu, onMenuChange } = this.props;
        return <header>
            <nav class="top-nav blue darken-1">
                <div class="container">
                    <div class="nav-wrapper"><a class="page-title">{acttiveMenu.title}</a></div>
                </div>
            </nav>
            <ul class="side-nav fixed">
                {
                    menus.filter(f => f.isRoot === true).valueSeq().map(menu => <li
                        class={isNil(menu.children) ? `bold${acttiveMenu.id === menu.id ? " active" : ""}` : "no-padding"}
                        key={menu.id}>
                        {
                            !isNil(menu.children)
                                ? <ul class="collapsible collapsible-accordion">
                                    <li class={`bold${acttiveMenu.id === menu.id ? " active" : ""}`}>
                                        <a class="collapsible-header  waves-effect waves-teal">{menu.title}</a>
                                        <div class="collapsible-body">
                                            <ul>
                                                {
                                                    menu.children.map((c) => {
                                                        const item = menus.get(c);
                                                        return <li>
                                                            <Link className="waves-effect" to={item.url} onClick={() => { onMenuChange(item.id) } }>{item.title}</Link>
                                                        </li>
                                                    })
                                                }
                                            </ul>
                                        </div>
                                    </li>
                                </ul>
                                : <Link className="waves-effect" to={menu.url} onClick={() => { onMenuChange(menu.id) } }>{menu.title}</Link>
                        }
                    </li>)
                }
            </ul>
        </header>
    }
}
const mapStateToProps = (state, ownProps) => {
    return {
        menus: getMenus(state),
        acttiveMenu: getActiveMenu(state)
    };
}

const mapDispatchToProps = dispatch => {
    return {
        onNavigateTo(dest) {
            dispatch(push(dest));
        },
        onMenuChange(menuId) {
            dispatch(changeMenu(menuId));
        }
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(MenuContainer);