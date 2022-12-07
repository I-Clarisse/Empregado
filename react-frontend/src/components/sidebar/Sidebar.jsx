import { Component } from "react";
import './sidebar.css'
import {GoDashboard} from 'react-icons/go'
import {HiUsers} from 'react-icons/hi'
import {FaUserPlus} from 'react-icons/fa'
import {FaUserCheck} from 'react-icons/fa'
import {IoMdSettings} from 'react-icons/io'
import { NavLink } from "react-router-dom";

class Sidebar extends Component {
    render() {
        return(
            <div className="wrapper">
                <div className="sidebar">
                    <div className="logo">
                        <h3>EmPREGADO</h3>
                    </div>
                    <ul>
                        <li>
                                <span className="icon"><GoDashboard/></span>
                                <NavLink className="item" to="/"> Dashboard</NavLink>
                            
                        </li>
                        <li>
                                <span className="icon"><HiUsers/></span>
                                <NavLink className="item"  to="/employees"> All Employees</NavLink>

                        </li>
                        <li>
                                <span className="icon"><FaUserCheck/></span>
                                <NavLink className="item"  to="/employees"> Current Employees</NavLink>
                            
                        </li>
                        <li>
                                <span className="icon"><FaUserPlus/></span>
                                <NavLink className="item" to="/add-employee"> Add Employee</NavLink>
                            
                        </li>
                        <li>
                                <span className="icon"><IoMdSettings/></span>
                                <NavLink className="item"  to="/"> Settings </NavLink>
                            
                        </li>
                    </ul>
                </div>
            </div>
        )
    }
}
export default Sidebar;