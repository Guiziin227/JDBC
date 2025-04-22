import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();
        Seller seller = sellerDao.findById(1);
        System.out.println("=== TESTE 1: seller findById ===");
        System.out.println(seller);

        System.out.println("\n=== TESTE 2: seller findByDepartment ===");
        Department department = new Department(2, null);

        List<Seller> list = sellerDao.findByDepartment(department);
        for (Seller obj : list) {
            System.out.println(obj);
        }

        System.out.println("\n=== TESTE 3: seller findAll ===");
        list = sellerDao.findAll();
        for (Seller obj : list) {
            System.out.println(obj);
        }

//        System.out.println("\n=== TESTE 4: seller insert ===");
//        Seller newSeller = new Seller(null, "Guilherme Weber", "gh44225@gmail.com", new Date(), 3000.0, department);
//        sellerDao.insert(newSeller);
//        System.out.println("Inserted! New id = " + newSeller.getId());

        System.out.println("\n=== TESTE 5: seller update ===");
        seller = sellerDao.findById(20);
        seller.setName("Biju");
        seller.setEmail("biju123@gmail.com");
        sellerDao.update(seller);
        System.out.println("Update completed!");

//        System.out.println("\n=== TESTE 6: seller delete ===");
//        sellerDao.deleteById(7);
//        System.out.println("Delete completed!");

        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
        Department department2 = departmentDao.findById(2);
        System.out.println("\n=== TESTE 7: seller findByIdDepartment ===");
        System.out.println(department2);

        System.out.println("\n=== TESTE 8: department findAll ===");
        List<Department> list2 = departmentDao.findAll();
        for (Department obj : list2) {
            System.out.println(obj);
        }

        System.out.println("\n=== TESTE 9: department insert ===");
        Department newDepartment = new Department(null, "Games");
        departmentDao.insert(newDepartment);
        System.out.println("Inserted! New id = " + newDepartment.getId());

        System.out.println("\n=== TESTE 10: department update ===");
        department2 = departmentDao.findById(2);
        department2.setName("Alquimias");
        departmentDao.update(department2);
        System.out.println("Update completed!");

        System.out.println("\n=== TESTE 11: department delete ===");
        departmentDao.deleteById(6);
    }
}