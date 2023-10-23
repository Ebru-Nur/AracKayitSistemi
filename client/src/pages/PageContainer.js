import React, { useEffect, useState } from "react";
import LeftBar from "../components/LeftBar";
import { Outlet, useNavigate } from "react-router-dom";
const PageContainer = () => {
 
  return (
    <div class="d-flex flex-row">
      <LeftBar />
      <Outlet />
    </div>
  );
};

export default PageContainer;
