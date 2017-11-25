import React from "react";
import ReactDOM from "react-dom";
import { push } from 'react-router-redux';
import { connect } from "react-redux";
import { getActiveMenu } from '../selectors/MenuSelectors';
import Title from 'react-title-component';
import InstallationContainer from './InstallationContainer';
import DatabaseContainer from './DatabaseContainer';
import EntityContainer from './EntityContainer';

class MainContainer extends React.Component {

    constructor(props) {
        super(props);
    }

    componentDidMount() {
    }

    componentDidUpdate(prevProps) {
    }

    render() {
        const {acttiveMenu} = this.props;
        let component = null;
        switch (acttiveMenu.id) {
            case "installation":
                component = <InstallationContainer />
                break;
            case "database":
                component = <DatabaseContainer />
                break;
            case "schema":
            case "store":
                component = <EntityContainer />
                break;
        }
        return <div>
            <Title render="Material-UI" />
            {component}
        </div>
    }
}

const mapStateToProps = (state, ownProps) => {
    return {
        acttiveMenu: getActiveMenu(state)
    };
}

const mapDispatchToProps = dispatch => {
    return {
        onNavigateTo(dest) {
            dispatch(push(dest));
        },
        onFetchChronology(accessToken, investigationId) {
            dispatch(fetchInvestigation(accessToken, investigationId));
        }
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(MainContainer);