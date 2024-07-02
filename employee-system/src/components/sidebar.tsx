import React from 'react';
import {useNavigate} from "react-router-dom";

interface SidebarProps{
    active:string
}

const Sidebar: React.FC = ({active}:SidebarProps) => {
    const navigate = useNavigate();
    return (
        <div className="w-1/5 bg-blue-900 text-white min-h-screen p-5">
            <h2 className="text-2xl font-bold mb-5">Employee Retention Prediction</h2>
            <ul>
                <li onClick={()=>navigate("/")} className={`mb-2 ${active==='dashboard'?`bg-blue-700`:``} cursor-pointer`}>
                    <a  className="block p-2 rounded hover:bg-blue-700 ">Dashboard</a>
                </li>
                <li onClick={()=>navigate("/check")}  className={`mb-2 ${active==='check'?`bg-blue-700`:``} cursor-pointer`}>
                    <a  className="block p-2 rounded hover:bg-blue-700">Train Data</a>
                </li>


            </ul>
        </div>
    );
};

export default Sidebar;
