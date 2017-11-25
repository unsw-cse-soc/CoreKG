import React, { Component } from 'react';
import { Link } from 'react-router';
import MenuContainer from '../containers/MenuContainer';

class Layout extends React.Component {

    constructor(props, context) {
        super(props, context);
    }

    render() {
        return (
            <div>
                <MenuContainer />
                <main>
                    <div class="container">
                        <div class="row">
                            <div class="col s12 m12 l12">
                                <div class="section scrollspy">
                                    {this.props.children}
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
            </div>
        );
    }
}

export default Layout;