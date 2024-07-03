import React from 'react';
import {useNavigate} from "react-router-dom";
import { MdOutlineDashboard } from "react-icons/md";
import { FiTool } from "react-icons/fi";
import logo from "../assets/logo-emp.png"

interface SidebarProps{
    active:string
}

const Sidebar: React.FC = ({active}:SidebarProps) => {
    const navigate = useNavigate();
    return (
        <div className="w-1/5 bg-blue-900 text-white min-h-screen p-5">
            <div className={"flex flex-col items-center gap-3 mb-5"}>
                <img alt={"logo"} src={logo} className={"h-20"}/>
                <h2 className="font-bold text-2xl text-center">Employee Retention Prediction</h2>
            </div>

            <ul>
                <li onClick={() => navigate("/")} className={`mb-2 flex items-center gap-2 ps-4 ${active ==='dashboard'?`bg-blue-700`:``} cursor-pointer`}>
                    <MdOutlineDashboard />
                    <a  className="block p-2 rounded hover:bg-blue-700 ">Dashboard</a>
                </li>
                <li onClick={()=>navigate("/check")}  className={`mb-2 flex items-center gap-2 ps-4 ${active==='check'?`bg-blue-700`:``} cursor-pointer`}>
                    <FiTool />
                    <a  className="block p-2 rounded hover:bg-blue-700">Train Data</a>
                </li>


            </ul>
        </div>
    );
};

export default Sidebar;
