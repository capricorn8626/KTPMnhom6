package BUS;

import DAO.SanPhamDAO;
import DTO.PhienBanSanPhamDTO;
import DTO.SanPhamDTO;
import java.util.ArrayList;

public class SanPhamBUS {

    public final SanPhamDAO spDAO = new SanPhamDAO();
    public PhienBanSanPhamBUS cauhinhBus = new PhienBanSanPhamBUS();
    private ArrayList<SanPhamDTO> listSP = new ArrayList<>();

    public SanPhamBUS() {
        listSP = spDAO.selectAll();
    }

    public ArrayList<SanPhamDTO> getAll() {
        
        return this.listSP;
    }

    public SanPhamDTO getByIndex(int index) {
        return this.listSP.get(index);
    }

    public SanPhamDTO getByMaSP(int masp) {
        int vitri = -1;
        int i = 0;
        while (i < this.listSP.size() && vitri == -1) {
            if (this.listSP.get(i).getMasp() == masp) {
                vitri = i;
            } else {
                i++;
            }
        }
        return this.listSP.get(vitri);
    }

    public int getIndexByMaSP(int masanpham) {
        int i = 0;
        int vitri = -1;
        while (i < this.listSP.size() && vitri == -1) {
            if (listSP.get(i).getMasp() == masanpham) {
                vitri = i;
            } else {
                i++;
            }
        }
        return vitri;
    }

    public Boolean add(SanPhamDTO lh, ArrayList<PhienBanSanPhamDTO> listch) {
        boolean check = spDAO.insert(lh) != 0;
        if (check) {
            cauhinhBus.add(listch);
            this.listSP.add(lh);
        }
        return check;
    }

    public Boolean delete(SanPhamDTO lh) {
        boolean check = spDAO.delete(Integer.toString(lh.getMasp())) != 0;
        if (check) {
            this.listSP.remove(lh);
        }
        return check;
    }

    public Boolean update(SanPhamDTO lh) {
        boolean check = spDAO.update(lh) != 0;
        if (check) {
            this.listSP.set(getIndexByMaSP(lh.getMasp()), lh);
        }
        return check;
    }

    public ArrayList<SanPhamDTO> getByMakhuvuc(int makv) {
        ArrayList<SanPhamDTO> result = new ArrayList<>();
        for (SanPhamDTO i : this.listSP) {
            if (i.getKhuvuckho() == makv) {
                result.add(i);
            }
        }
        return result;
    }

    public ArrayList<SanPhamDTO> search(String text) {
        text = text.toLowerCase();
        ArrayList<SanPhamDTO> result = new ArrayList<>();
        for (SanPhamDTO i : this.listSP) {
            if (Integer.toString(i.getMasp()).toLowerCase().contains(text)
                    || i.getTensp().toLowerCase().contains(text)) {
                result.add(i);
            }
        }
        return result;
    }
    
    public ArrayList<SanPhamDTO> search(String field, String text) {
        text = text.toLowerCase();
        ArrayList<SanPhamDTO> result = new ArrayList<>();
        ThuongHieuBUS thuongHieuBUS = new ThuongHieuBUS();
        HeDieuHanhBUS heDieuHanhBUS = new HeDieuHanhBUS();
        XuatXuBUS xuatXuBUS = new XuatXuBUS();
        KhuVucKhoBUS khuVucKhoBUS = new KhuVucKhoBUS();
        // {"Mã sản phẩm", "Tên sản phẩm", "Thương hiệu", "Hệ điều hành", "Xuất xứ", "Kho hàng"}
        for (SanPhamDTO i : this.listSP) {
            String masp = Integer.toString(i.getMasp()).toLowerCase();
            String tensp = i.getTensp().toLowerCase();
            String thuonghieu = thuongHieuBUS.getTenThuongHieu(i.getThuonghieu()).toLowerCase();
            String hedieuhanh = heDieuHanhBUS.getTenHdh(i.getHedieuhanh()).toLowerCase();
            String xuatxu = xuatXuBUS.getTenXuatXu(i.getXuatxu()).toLowerCase();
            String khohang = khuVucKhoBUS.getTenKhuVuc(i.getKhuvuckho()).toLowerCase();
            switch (field) {
                case "Mã sản phẩm":
                    if (masp.contains(text)) {
                        result.add(i);
                    }
                    break;
                case "Tên sản phẩm":
                    if (tensp.contains(text)) {
                        result.add(i);
                    }
                    break;
                case "Thương hiệu":
                    if (thuonghieu.contains(text)) {
                        result.add(i);
                    }
                    break;
                case "Hệ điều hành":
                    if (hedieuhanh.contains(text)) {
                        result.add(i);
                    }
                    break;
                case "Xuất xứ":
                    if (xuatxu.contains(text)) {
                        result.add(i);
                    }
                    break;
                case "Kho hàng":
                    if (khohang.contains(text)) {
                        result.add(i);
                    }
                    break;
            }
        }
        return result;
    }

    public SanPhamDTO getSp(int mapb) {
        return spDAO.selectByPhienBan(mapb + "");
    }

    public int getQuantity() {
        ArrayList<SanPhamDTO> result = new ArrayList<>();
        int n = 0;
        for(SanPhamDTO i : this.listSP) {
            if (i.getSoluongton() != 0) {
                n += i.getSoluongton();
            }
        }
        return n;
    }
}
